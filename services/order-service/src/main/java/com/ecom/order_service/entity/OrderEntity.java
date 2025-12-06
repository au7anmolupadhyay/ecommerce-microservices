package com.ecom.order_service.entity;

import com.ecom.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double price;
    private Long productId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Integer quantity;
    private Date createdAt;
}
