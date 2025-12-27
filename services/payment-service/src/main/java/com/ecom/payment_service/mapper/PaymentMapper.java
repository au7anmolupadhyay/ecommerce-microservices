package com.ecom.payment_service.mapper;

import com.ecom.payment_service.dto.CreatePaymentRequestDto;
import com.ecom.payment_service.dto.PaymentResponseDto;
import com.ecom.payment_service.dto.PaymentSummaryResponseDto;
import com.ecom.payment_service.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {

    // Request → Entity
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "gatewayTransactionId", ignore = true)
    @Mapping(target = "failureReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentEntity toEntity(CreatePaymentRequestDto request);

    // Entity → Response
    PaymentResponseDto toResponse(PaymentEntity entity);

    // Entity → Summary
    PaymentSummaryResponseDto toSummary(PaymentEntity entity);

    // List mappings
    List<PaymentResponseDto> toResponseList(List<PaymentEntity> entities);
    List<PaymentSummaryResponseDto> toSummaryList(List<PaymentEntity> entities);
}
