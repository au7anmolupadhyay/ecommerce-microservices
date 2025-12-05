package com.ecom.inventory_service.service;

import com.ecom.inventory_service.dto.StockCheckResponseDTO;
import com.ecom.inventory_service.dto.StockResponseDTO;
import com.ecom.inventory_service.dto.StockUpdateRequestDTO;
import com.ecom.inventory_service.entity.InventoryEntity;
import com.ecom.inventory_service.mapper.InventoryMapper;
import com.ecom.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public StockResponseDTO addProductQuantity(StockUpdateRequestDTO product){
        InventoryEntity stock = inventoryRepository.findByProductId(product.getProductId())
                .orElse(new InventoryEntity(null, product.getProductId(), 0));

        stock.setQuantity(stock.getQuantity() + product.getQuantity());
        stock = inventoryRepository.save(stock);

        return inventoryMapper.toDto(stock);
    }

    public StockResponseDTO reduceProductQuantity(StockUpdateRequestDTO product){
        InventoryEntity stock = inventoryRepository.findByProductId(product.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found!"));

        if(stock.getQuantity() < product.getQuantity()){
            throw new RuntimeException ("Insufficient Stock");
        }

        stock.setQuantity(stock.getQuantity() - product.getQuantity());
        stock = inventoryRepository.save(stock);

        return inventoryMapper.toDto(stock);
    }

    public StockCheckResponseDTO checkStockQuantity(Long productId){
        InventoryEntity stock = inventoryRepository.findByProductId(productId)
                .orElse(new InventoryEntity(null, productId, 0));

        StockCheckResponseDTO response = new StockCheckResponseDTO();
        response.setProductId(productId);
        response.setQuantity(stock.getQuantity());
        response.setAvailable(stock.getQuantity()>0);

        return response;
    }
}
