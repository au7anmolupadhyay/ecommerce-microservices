package com.ecom.inventory_service.controller;

import com.ecom.inventory_service.dto.StockCheckResponseDTO;
import com.ecom.inventory_service.dto.StockResponseDTO;
import com.ecom.inventory_service.dto.StockUpdateRequestDTO;
import com.ecom.inventory_service.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<StockResponseDTO> addStock(@RequestBody StockUpdateRequestDTO product){
        return ResponseEntity.ok(inventoryService.addProductQuantity(product));
    }

    @PostMapping("/reduce")
    public ResponseEntity<StockResponseDTO> reduceStock(@RequestBody StockUpdateRequestDTO product){
        return ResponseEntity.ok(inventoryService.reduceProductQuantity(product));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<StockCheckResponseDTO> checkStock(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.checkStockQuantity(productId));
    }

}
