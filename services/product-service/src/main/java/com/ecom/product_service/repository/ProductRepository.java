package com.ecom.product_service.repository;

import com.ecom.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryIgnoreCase(String category);
}
