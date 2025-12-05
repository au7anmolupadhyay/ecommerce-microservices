package com.ecom.inventory_service.dto;

import lombok.Data;

@Data
public class StockResponseDTO {
    private Long productId;
    private Integer quantity;
}
