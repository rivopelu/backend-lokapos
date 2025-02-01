package com.lokapos.repositories;

import com.lokapos.entities.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Page<Merchant> findByBusinessIdAndActiveIsTrue(Pageable pageable, String businessId);

    @Query("select m from  Merchant  as m order by  m.createdDate desc limit 5")
    List<Merchant> getTopMerchant();
}
