package com.ecom.order_service.client;

import com.ecom.order_service.dto.StockCheckResponseDTO;
import com.ecom.order_service.dto.StockUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "http://localhost:8083")
public interface InventoryClient {

    @GetMapping("/inventory/check/{productId}")
    StockCheckResponseDTO checkStock(@PathVariable Long productId);

    @PostMapping("/inventory/reduce")
    Boolean reduceStock(@RequestBody StockUpdateRequestDTO product);
}
