package com.crown.controller;

import com.crown.dto.OrderRequest;
import com.crown.dto.OrderResponse;
import com.crown.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for order processing.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/orders
     * Receives the full cart + shipping address, validates, persists, and returns success.
     *
     * In a production scenario, this endpoint would require:
     *   - Authorization: Bearer <jwt_token>
     *   - The token would be decoded to identify the user_id
     * For the demo, orders are saved without user association.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
