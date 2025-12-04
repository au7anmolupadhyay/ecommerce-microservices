package com.ecom.product_service.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String brand;
    private String imageUrl;
}
