package com.lokapos.repositories;

import com.lokapos.entities.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, String> {
}
