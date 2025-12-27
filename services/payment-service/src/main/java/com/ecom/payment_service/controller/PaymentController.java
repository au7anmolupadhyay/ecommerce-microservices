package com.ecom.payment_service.controller;

import com.ecom.payment_service.dto.CreatePaymentRequestDto;
import com.ecom.payment_service.dto.UpdatePaymentStatusRequestDto;
import com.ecom.payment_service.entity.PaymentEntity;
import com.ecom.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody CreatePaymentRequestDto paymentRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(paymentRequest));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable String paymentId){
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentDetailsByOrderId(@PathVariable String orderId){
        return ResponseEntity.ok(paymentService.getPaymentDetailsByOrderId(orderId));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable String paymentId,
                                                 @RequestBody UpdatePaymentStatusRequestDto updateRequest){
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, updateRequest));
    }

    @GetMapping
    public ResponseEntity<List<PaymentEntity>> getAllPayments(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(
                paymentService.getAllPayments(userId, status)
        );
    }

}
