package com.lokapos.repositories;

import com.lokapos.entities.PaymentAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentActionRepository extends JpaRepository<PaymentAction, String> {
}
