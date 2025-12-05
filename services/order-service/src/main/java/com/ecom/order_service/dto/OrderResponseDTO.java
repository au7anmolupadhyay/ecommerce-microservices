package com.ecom.order_service.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long userId;
    private Long productId;
    private Long orderId;
    private String orderStatus;
    private Integer quantity;
}
