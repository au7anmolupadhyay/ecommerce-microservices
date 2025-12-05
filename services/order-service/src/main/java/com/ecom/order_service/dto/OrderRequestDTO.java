package com.ecom.order_service.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long productId;
    private Long userId;
    private Integer quantity;
}
