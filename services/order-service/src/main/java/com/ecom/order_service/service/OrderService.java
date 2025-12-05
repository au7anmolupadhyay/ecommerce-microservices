package com.ecom.order_service.service;

import com.ecom.order_service.dto.OrderRequestDTO;
import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.mapper.OrderMapper;
import com.ecom.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponseDTO placeOrder(OrderRequestDTO order){

    }

    public OrderResponseDTO getOrderDetails(Long orderId){

    }

    public List<OrderResponseDTO> getAllOrdersOfUser(Long userId){

    }

    public boolean cancelOrder(Long orderId){

    }
}
