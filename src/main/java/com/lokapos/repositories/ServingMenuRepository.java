package com.lokapos.repositories;

import com.lokapos.entities.ServingMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServingMenuRepository extends JpaRepository<ServingMenu, String> {
    List<ServingMenu> findAllByBusinessIdAndActiveIsTrueOrderByCreatedDateDesc(String businessId);
}
