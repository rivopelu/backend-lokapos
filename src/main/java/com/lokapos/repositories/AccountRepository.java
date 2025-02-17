package com.lokapos.repositories;

import com.lokapos.entities.Account;
import com.lokapos.enums.USER_ROLE_ENUM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmailAndActiveIsTrue(String username);

    boolean existsByEmailAndActiveIsTrue(String email);

    Page<Account> findAllByBusinessIdAndActiveIsTrueOrderByCreatedDateDesc(Pageable pageable, String businessId);

    List<Account> findByActiveShiftId(String shiftId);

    List<Account> findByBusinessIdAndActiveIsTrueAndRole(String business_id, USER_ROLE_ENUM role);
}
