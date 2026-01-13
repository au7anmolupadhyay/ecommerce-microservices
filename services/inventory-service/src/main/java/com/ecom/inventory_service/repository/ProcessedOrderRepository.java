package com.ecom.inventory_service.repository;

import com.ecom.inventory_service.entity.ProcessedOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrderEntity, Long> {
}
