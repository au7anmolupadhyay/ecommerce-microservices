package com.ecom.inventory_service.kafka;

import com.ecom.inventory_service.event.OrderCreatedEvent;
import com.ecom.inventory_service.entity.InventoryEntity;
import com.ecom.inventory_service.repository.InventoryRepository;
import com.ecom.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void handleOrderCreated(OrderCreatedEvent event, Acknowledgment ack) {
        try{
            inventoryService.reduceStockForOrder(event.getOrderId(), event.getProductId(), event.getQuantity());
            System.out.println("stock reduced for order : " + event.getOrderId());
            ack.acknowledge();
        }
        catch (Exception e){
            System.out.println("Inventory Failed for order " + event.getOrderId());
            throw e;
        }
    }
}

