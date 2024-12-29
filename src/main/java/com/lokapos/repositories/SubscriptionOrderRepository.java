package com.lokapos.repositories;

import com.lokapos.entities.SubscriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, String> {
}
