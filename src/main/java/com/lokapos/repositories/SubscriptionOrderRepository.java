package com.lokapos.repositories;

import com.lokapos.entities.SubscriptionOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, String> {
    Page<SubscriptionOrder> findAllByBusinessIdAndActiveIsTrueOrderByCreatedDateDesc(String businessId, Pageable pageable);
}
