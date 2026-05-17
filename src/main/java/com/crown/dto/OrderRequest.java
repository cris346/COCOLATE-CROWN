package com.crown.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

/**
 * DTO that the frontend sends to POST /api/orders.
 * Contains cart items + shipping address.
 */
@Data
public class OrderRequest {

    @NotNull(message = "La dirección de envío es obligatoria")
    @Valid
    private ShippingAddressDto shippingAddress;

    @NotEmpty(message = "El carrito no puede estar vacío")
    @Valid
    private List<CartItemDto> cart;
}
