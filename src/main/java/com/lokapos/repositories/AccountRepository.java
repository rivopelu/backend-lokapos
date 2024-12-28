package com.lokapos.repositories;

import com.lokapos.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmailAndActiveIsTrue(String username);

    boolean existsByEmailAndActiveIsTrue(String email);

    Page<Account> findAllByBusinessIdAndActiveIsTrue(Pageable pageable, String businessId);
}
