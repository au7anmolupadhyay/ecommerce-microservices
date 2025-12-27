package com.ecom.payment_service.dto;

import com.ecom.payment_service.enums.PaymentMethod;
import com.ecom.payment_service.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentResponseDto {

    private String paymentId;
    private String orderId;
    private String userId;

    private BigDecimal amount;
    private String currency;

    private PaymentMethod paymentMethod;
    private PaymentStatus status;

    private String gatewayTransactionId;
    private String failureReason;

    private Instant createdAt;
    private Instant updatedAt;
}
