package com.ecom.inventory_service.controller;

import com.ecom.inventory_service.dto.StockUpdateRequestDTO;
import com.ecom.inventory_service.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody StockUpdateRequestDTO product){
        return ResponseEntity.(inventoryService.addProductQuantity(product));
    }

    @PostMapping("/reduce")
    public ResponseEntity<?> reduceStock(@RequestBody StockUpdateRequestDTO product){
        return ResponseEntity.ok(inventoryService.reduceProductQuantity(product));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<?> checkStock(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.checkStockQuantity(productId));
    }

}
