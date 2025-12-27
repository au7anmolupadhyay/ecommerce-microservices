package com.ecom.payment_service.dto;

import com.ecom.payment_service.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentSummaryResponseDto {

    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant createdAt;
}
