package com.lokapos.repositories;

import com.lokapos.entities.ServingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServingOrderRepository extends JpaRepository<ServingOrder, String> {
    Page<ServingOrder> findAllByBusinessIdAndActiveTrueOrderByCreatedDateDesc(Pageable pageable, String businessId);


    @Query("select o from ServingOrder  as o where o.business.id = :businessId order by o.createdDate desc limit 1")
    Optional<ServingOrder> findLatestData(String businessId);


}
