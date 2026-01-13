package com.ecom.inventory_service.service;

import com.ecom.inventory_service.dto.StockCheckResponseDTO;
import com.ecom.inventory_service.dto.StockResponseDTO;
import com.ecom.inventory_service.dto.StockUpdateRequestDTO;
import com.ecom.inventory_service.entity.InventoryEntity;
import com.ecom.inventory_service.entity.ProcessedOrderEntity;
import com.ecom.inventory_service.mapper.InventoryMapper;
import com.ecom.inventory_service.repository.InventoryRepository;
import com.ecom.inventory_service.repository.ProcessedOrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProcessedOrderRepository processedOrderRepository;

    public StockResponseDTO addProductQuantity(StockUpdateRequestDTO product){
        InventoryEntity stock = inventoryRepository.findByProductId(product.getProductId())
                .orElse(new InventoryEntity(product.getProductId(), 0));

        stock.setQuantity(stock.getQuantity() + product.getQuantity());
        stock = inventoryRepository.save(stock);

        return inventoryMapper.toDto(stock);
    }

//    public Boolean reduceProductQuantity(StockUpdateRequestDTO product){
//        InventoryEntity stock = inventoryRepository.findByProductId(product.getProductId())
//                .orElseThrow(()->new RuntimeException("Product not found!"));
//
//        if(stock.getQuantity() < product.getQuantity()){
//            throw new RuntimeException ("Insufficient Stock");
//        }
//
//        try{
//            stock.setQuantity(stock.getQuantity() - product.getQuantity());
//            inventoryRepository.save(stock);
//            return true;
//        }catch (Exception e){
//            throw new RuntimeException("Stock not updated");
//        }
//    }

    @Transactional
    public void reduceStockForOrder(Long orderId, Long productId, Integer quantity) {

        // idempotency
        if(processedOrderRepository.existsById(orderId)){
            System.out.println("Order already processed : " + orderId);
            return;
        }

        InventoryEntity stock = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient Stock");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        inventoryRepository.save(stock);

        processedOrderRepository.save(new ProcessedOrderEntity(orderId));
    }


    public StockCheckResponseDTO checkStockQuantity(Long productId){
        InventoryEntity stock = inventoryRepository.findByProductId(productId)
                .orElse(new InventoryEntity(productId, 0));

        StockCheckResponseDTO response = new StockCheckResponseDTO();
        response.setProductId(productId);
        response.setQuantity(stock.getQuantity());
        response.setAvailable(stock.getQuantity()>0);

        return response;
    }
}
