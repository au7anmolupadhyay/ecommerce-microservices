package com.ecom.payment_service.service.impl;

import com.ecom.payment_service.dto.UpdatePaymentStatusRequestDto;
import com.ecom.payment_service.entity.PaymentEntity;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exception.InvalidPaymentStateException;
import com.ecom.payment_service.exception.PaymentNotFoundException;
import com.ecom.payment_service.repository.PaymentRepository;
import com.ecom.payment_service.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public PaymentEntity updatePaymentStatus(
            String paymentId,
            UpdatePaymentStatusRequestDto request
    ){

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found: " + paymentId
                        )
                );

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Payment already in final state: " + payment.getStatus()
            );
        }

        // Duplicate gateway txn protection
        if (request.getGatewayTransactionId() != null &&
                paymentRepository
                        .findByGatewayTransactionId(request.getGatewayTransactionId())
                        .isPresent()) {

            throw new InvalidPaymentStateException(
                    "Duplicate gateway transaction"
            );
        }

        // Update state
        payment.setStatus(request.getStatus());
        payment.setGatewayTransactionId(request.getGatewayTransactionId());

        // Store failure reason ONLY if failed
        if (request.getStatus() == PaymentStatus.FAILED) {
            payment.setFailureReason(request.getFailureReason());
        }

        return paymentRepository.save(payment);
    }
}
