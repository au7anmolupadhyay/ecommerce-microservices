package com.ecom.payment_service.dto;

import com.ecom.payment_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequestDto {
    @NotNull
    private String orderId;
    @NotNull
    private String userId;
    @NotNull
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
}
