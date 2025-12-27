package com.ecom.payment_service.dto;

import com.ecom.payment_service.enums.PaymentStatus;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequestDto {
    private PaymentStatus status;
    private String gatewayTransactionId;
    private String failureReason;
}
