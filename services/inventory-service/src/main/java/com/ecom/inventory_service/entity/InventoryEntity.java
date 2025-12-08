package com.ecom.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity {

    @Id
    private Long productId;
    private Integer quantity;
}
