package com.lokapos.repositories;

import com.lokapos.entities.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Page<Merchant> findByBusinessIdAndActiveIsTrue(Pageable pageable,String businessId);
}
