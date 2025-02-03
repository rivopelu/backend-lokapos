package com.lokapos.repositories;

import com.lokapos.entities.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {

    @Query(value = "select wt from WalletTransaction as wt  where  wt.active = true  and  wt.business.id =:businessId order by wt.updatedDate desc ")
    Page<WalletTransaction> findByBusinessIdAndActiveIsTrue(Pageable pageable, String businessId);
}
