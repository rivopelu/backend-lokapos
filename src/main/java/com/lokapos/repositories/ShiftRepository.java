package com.lokapos.repositories;

import com.lokapos.entities.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, String> {

    @Query("select  s from  Shift  as s join ShiftAccount  as sa " +
            "on s.id = sa.shift.id where sa.account.id = :accountId ")
    Page<Shift> getListStaffShift(String accountId, Pageable pageable);
}
