package com.ecom.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "order")
@NoArgsConstructor
@RequiredArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double price;
    private Long productId;
    private String orderStatus;
    private Integer quantity;
    private Date createdAt;
}
