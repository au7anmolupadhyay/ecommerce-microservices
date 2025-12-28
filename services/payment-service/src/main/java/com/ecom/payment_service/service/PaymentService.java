package com.ecom.payment_service.service;

import com.ecom.payment_service.dto.CreatePaymentRequestDto;
import com.ecom.payment_service.dto.PaymentResponseDto;
import com.ecom.payment_service.dto.PaymentSummaryResponseDto;
import com.ecom.payment_service.dto.UpdatePaymentStatusRequestDto;
import com.ecom.payment_service.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    PaymentResponseDto createPayment(CreatePaymentRequestDto request);

    PaymentResponseDto getPaymentDetails(String paymentId);

    PaymentResponseDto getPaymentDetailsByOrderId(String orderId);

    PaymentResponseDto updatePaymentStatus(String paymentId, UpdatePaymentStatusRequestDto request);

    List<PaymentSummaryResponseDto> getAllPayments(String userId, PaymentStatus status);
}