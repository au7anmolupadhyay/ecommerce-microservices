package com.ecom.order_service.controller;

import com.ecom.order_service.dto.OrderRequestDTO;
import com.ecom.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(OrderRequestDTO order){
        return ResponseEntity.created(orderService.placeOrder(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllOrderOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getAllOrdersOfUser(userId));
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.accepted(orderService.cancelOrder(orderId));
    }

}
