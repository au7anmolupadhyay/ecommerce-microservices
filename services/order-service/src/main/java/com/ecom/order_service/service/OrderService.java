package com.ecom.order_service.service;

import com.ecom.order_service.client.InventoryClient;
import com.ecom.order_service.dto.OrderRequestDTO;
import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.dto.StockCheckResponseDTO;
import com.ecom.order_service.dto.StockUpdateRequestDTO;
import com.ecom.order_service.entity.OrderEntity;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.mapper.OrderMapper;
import com.ecom.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;

    public OrderResponseDTO placeOrder(OrderRequestDTO order){
        System.out.println("inside orderService placeorder" + order);
        StockCheckResponseDTO stock = inventoryClient.checkStock(order.getProductId());

        if(stock.getQuantity() < order.getQuantity())
            throw new RuntimeException("Out of Stock");

        Boolean reduced = inventoryClient.
                reduceStock(new StockUpdateRequestDTO(order.getProductId(), order.getQuantity()));

        if(!reduced) throw new RuntimeException("Failed to reduce the stock");

        System.out.println("inside 2 orderService place order" + order);
        OrderEntity newOrder = new OrderEntity();
        newOrder.setUserId(order.getUserId());
        newOrder.setProductId(order.getProductId());
        newOrder.setQuantity(order.getQuantity());
        newOrder.setPrice(0.0);
        newOrder.setOrderStatus(OrderStatus.PLACED);
        newOrder.setCreatedAt(new Date());

        OrderEntity orderCreated = orderRepository.save(newOrder);
        return orderMapper.toDto(orderCreated);
    }

    public OrderResponseDTO getOrderDetails(Long orderId){
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        return orderMapper.toDto(order);
    }

    public List<OrderResponseDTO> getAllOrdersOfUser(Long userId){
        List<OrderEntity> ordersList =  orderRepository.findByUserId(userId);

        return ordersList.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public boolean cancelOrder(Long orderId){
        OrderEntity order = orderRepository
                .findById(orderId).orElseThrow(()->new RuntimeException("order not found"));

        if(order.getOrderStatus().equals(OrderStatus.CANCELLED)) return false;

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return true;
    }
}
