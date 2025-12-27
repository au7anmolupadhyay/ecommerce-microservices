package com.ecom.payment_service.dto;

import com.ecom.payment_service.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequestDto {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
}
