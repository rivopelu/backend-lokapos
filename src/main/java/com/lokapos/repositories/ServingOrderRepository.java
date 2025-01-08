package com.lokapos.repositories;

import com.lokapos.entities.ServingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServingOrderRepository extends JpaRepository<ServingOrder, String> {
}
