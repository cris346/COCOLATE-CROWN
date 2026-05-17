package com.crown.service;

import com.crown.dto.*;
import com.crown.entity.*;
import com.crown.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Handles order processing: validates cart, recalculates prices from DB,
 * persists the order with all items and chocolates.
 *
 * Architecture note: This service is structured so a PaymentService can be
 * injected in the future without modifying the core order logic.
 * Simply add a PaymentService call between validation and the final status update.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final BoxSizeRepository boxSizeRepository;
    private final ChocolateFlavorRepository chocolateFlavorRepository;
    private final FillingRepository fillingRepository;

    // TODO: Inject PaymentService here when implementing real payments.
    // private final PaymentService paymentService;

    /**
     * Process an order from the frontend.
     * 1. Validate each cart item against the DB catalog.
     * 2. Recalculate total price from DB (don't trust frontend price).
     * 3. Persist Order -> OrderItems -> BoxChocolates.
     * 4. Return success response.
     */
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Processing new order with {} items", request.getCart().size());

        Order order = Order.builder()
                .status("PENDIENTE_DEMO")
                .totalPrice(BigDecimal.ZERO)
                // Address snapshot
                .calleNumero(request.getShippingAddress().getCalleNumero())
                .colonia(request.getShippingAddress().getColonia())
                .codigoPostal(request.getShippingAddress().getCodigoPostal())
                .ciudad(request.getShippingAddress().getCiudad())
                .estado(request.getShippingAddress().getEstado())
                .build();

        BigDecimal calculatedTotal = BigDecimal.ZERO;

        for (CartItemDto cartItem : request.getCart()) {
            // 1. Resolve box size from DB
            BoxSize boxSize = boxSizeRepository.findByName(cartItem.getBoxSize())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tamaño de caja no encontrado: " + cartItem.getBoxSize()));

            // 2. Validate chocolate count matches capacity
            if (cartItem.getChocolates().size() != boxSize.getCapacity()) {
                throw new IllegalArgumentException(
                        String.format("La caja '%s' requiere exactamente %d chocolates, pero se recibieron %d",
                                boxSize.getName(), boxSize.getCapacity(), cartItem.getChocolates().size()));
            }

            // 3. Build OrderItem with server-side price
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .boxSize(boxSize)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(boxSize.getBasePrice())
                    .build();

            // 4. Resolve each chocolate's flavor and filling from DB
            for (ChocolateDto chocoDto : cartItem.getChocolates()) {
                ChocolateFlavor flavor = chocolateFlavorRepository.findByName(chocoDto.getBaseFlavor())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Sabor no encontrado: " + chocoDto.getBaseFlavor()));

                Filling filling = fillingRepository.findByName(chocoDto.getFilling())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Relleno no encontrado: " + chocoDto.getFilling()));

                BoxChocolate boxChocolate = BoxChocolate.builder()
                        .orderItem(orderItem)
                        .chocolateFlavor(flavor)
                        .filling(filling)
                        .build();

                orderItem.getChocolates().add(boxChocolate);
            }

            order.getItems().add(orderItem);

            // 5. Accumulate total (price * quantity)
            calculatedTotal = calculatedTotal.add(
                    boxSize.getBasePrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        // Set server-calculated total
        order.setTotalPrice(calculatedTotal);

        // =============================================
        // TODO: PAYMENT GATEWAY INTEGRATION POINT
        // At this point, call paymentService.processPayment(order)
        // If successful, set status to 'PAID'
        // If failed, throw exception and roll back
        // For now, we simulate success:
        order.setStatus("DEMO_PROCESADA");
        // =============================================

        Order savedOrder = orderRepository.save(order);
        log.info("Order #{} created successfully. Total: ${}", savedOrder.getId(), calculatedTotal);

        return OrderResponse.builder()
                .success(true)
                .orderId(savedOrder.getId())
                .message("¡Pedido realizado con éxito! Tu demostración ha sido procesada.")
                .build();
    }
}
