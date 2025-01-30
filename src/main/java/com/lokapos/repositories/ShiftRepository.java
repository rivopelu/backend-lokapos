package com.lokapos.repositories;

import com.lokapos.entities.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShiftRepository extends JpaRepository<Shift, String> {

    @Query("select  s from  Shift  as s join ShiftAccount  as sa " +
            "on s.id = sa.shift.id where sa.account.id = :accountId order by s.createdDate desc")
    Page<Shift> getListStaffShift(String accountId, Pageable pageable);

    @Query("select  s from Shift  as s where s.business.id = :business order by s.createdDate desc ")
    Page<Shift> getShiftByBusinessId(String business, Pageable pageable);
}
