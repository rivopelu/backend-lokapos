package com.lokapos.repositories;

import com.lokapos.entities.ShiftAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftAccountRepository extends JpaRepository<ShiftAccount, String> {
}
