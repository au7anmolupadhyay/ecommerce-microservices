package com.ecom.payment_service.service.impl;

import com.ecom.payment_service.dto.CreatePaymentRequestDto;
import com.ecom.payment_service.dto.PaymentResponseDto;
import com.ecom.payment_service.dto.PaymentSummaryResponseDto;
import com.ecom.payment_service.dto.UpdatePaymentStatusRequestDto;
import com.ecom.payment_service.entity.PaymentEntity;
import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exception.InvalidPaymentStateException;
import com.ecom.payment_service.exception.PaymentNotFoundException;
import com.ecom.payment_service.mapper.PaymentMapper;
import com.ecom.payment_service.repository.PaymentRepository;
import com.ecom.payment_service.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    @Override
    @Transactional
    public PaymentResponseDto createPayment(CreatePaymentRequestDto request) {
        PaymentEntity existingPayment = paymentRepository.findByOrderId(request.getOrderId())
                .orElse(null);

        if(existingPayment != null){
            return mapper.toResponse(existingPayment);
        }

        // converting to their correct format dto or entity based on the usage
        PaymentEntity paymentEntity = mapper.toEntity(request); // status = pending will be handled by mapper
        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);

        return mapper.toResponse(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentDetails(String paymentId){
        PaymentEntity existingPayment = paymentRepository.findById(paymentId).orElseThrow(()->
                new PaymentNotFoundException("Payment not found for paymentId: " + paymentId));

        return mapper.toResponse(existingPayment);
    }

    @Override
    public PaymentResponseDto getPaymentDetailsByOrderId(String orderId){
        PaymentEntity existingPayment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(()->
                    new PaymentNotFoundException("Payment not found with orderId: " + orderId));

        return mapper.toResponse(existingPayment);
    }

    @Override
    @Transactional
    public PaymentResponseDto updatePaymentStatus(String paymentId, UpdatePaymentStatusRequestDto request){

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

        paymentRepository.save(payment);
        return mapper.toResponse(payment);
    }

    @Override
    public List<PaymentSummaryResponseDto> getAllPayments(String userId, PaymentStatus status){
        if(userId == null && status == null){
            List<PaymentEntity> allPayments = paymentRepository.findAll();
            return mapper.toSummaryList(allPayments);
        }

        if(userId != null && status == null){
            List<PaymentEntity> allPaymentsByUser = paymentRepository.findByUserId(userId);
            return mapper.toSummaryList(allPaymentsByUser);
        }

        if(userId == null && status != null){
            List<PaymentEntity> allPaymentsByStatus = paymentRepository.findByStatus(status);
            return mapper.toSummaryList(allPaymentsByStatus);
        }

        List<PaymentEntity> allPaymentsByUserWithStatus = paymentRepository.findByUserIdAndStatus(userId, status);
        return mapper.toSummaryList(allPaymentsByUserWithStatus);
    }
}
