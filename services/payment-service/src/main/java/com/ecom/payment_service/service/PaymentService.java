package com.ecom.payment_service.service;

import com.ecom.payment_service.dto.UpdatePaymentStatusRequestDto;
import com.ecom.payment_service.entity.PaymentEntity;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentEntity updatePaymentStatus(
            String paymentId,
            UpdatePaymentStatusRequestDto request
    );
}