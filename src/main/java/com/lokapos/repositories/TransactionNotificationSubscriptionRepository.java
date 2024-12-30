package com.lokapos.repositories;

import com.lokapos.entities.TransactionNotificationSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionNotificationSubscriptionRepository extends JpaRepository<TransactionNotificationSubscription, String> {
}
