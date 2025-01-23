package com.lokapos.repositories;

import com.lokapos.entities.PaymentAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentActionRepository extends JpaRepository<PaymentAction, String> {
    Optional<PaymentAction> findByServingOrderIdAndName(String orderId, String name);
}
