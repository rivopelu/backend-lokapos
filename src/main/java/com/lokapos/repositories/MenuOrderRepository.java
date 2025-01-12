package com.lokapos.repositories;

import com.lokapos.entities.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, String> {
    List<MenuOrder> findAllByServingOrderId(String id);
}
