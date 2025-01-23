package com.lokapos.repositories;

import com.lokapos.entities.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, String> {
    List<CategoryMenu> findAllByBusinessIdAndActiveIsTrueOrderByCountDesc(String businessId);
}
