package com.lokapos.repositories;

import com.lokapos.entities.ServingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServingOrderRepository extends JpaRepository<ServingOrder, String> {
    Page<ServingOrder> findAllByBusinessIdAndActiveTrueOrderByCreatedDateDesc(Pageable pageable, String businessId);
}
