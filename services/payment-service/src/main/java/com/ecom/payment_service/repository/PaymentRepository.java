package com.ecom.payment_service.repository;

import com.ecom.payment_service.entity.PaymentEntity;
import com.ecom.payment_service.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    // enforces idempotency
    Optional<PaymentEntity> findByOrderId(String orderId);

    List<PaymentEntity> findByUserId(String userId);

    // sometimes gateway sends multiple callbacks for one transaction, so we should not update the status twice or
    // do any type of trigger for the duplicate callbacks
    Optional<PaymentEntity> findByGatewayTransactionId(String gatewayTransactionId);

    List<PaymentEntity> findByStatus(PaymentStatus paymentStatus);

    List<PaymentEntity> findByUserIdAndStatus(String userId, PaymentStatus status);
}
