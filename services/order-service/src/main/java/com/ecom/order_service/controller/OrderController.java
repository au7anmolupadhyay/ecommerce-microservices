package com.ecom.order_service.controller;

import com.ecom.order_service.dto.OrderRequestDTO;
import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO order){
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderDetails(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrderOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getAllOrdersOfUser(userId));
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

}
