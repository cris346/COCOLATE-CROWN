package com.crown.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private boolean success;
    private Long orderId;
    private String message;
}
