package com.ecom.inventory_service.dto;

import lombok.Data;

@Data
public class StockUpdateRequestDTO {
    private Long productId;
    private Integer quantity;
}
