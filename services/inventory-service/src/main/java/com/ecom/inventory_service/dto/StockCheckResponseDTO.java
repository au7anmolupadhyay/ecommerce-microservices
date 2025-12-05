package com.ecom.inventory_service.dto;

import lombok.Data;

@Data
public class StockCheckResponseDTO {
    private Long productId;
    private boolean available;
    private Integer quantity;
}
