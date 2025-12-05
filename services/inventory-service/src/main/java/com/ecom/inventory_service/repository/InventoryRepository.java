package com.ecom.inventory_service.repository;

import com.ecom.inventory_service.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByProductId(Long productId);
}
