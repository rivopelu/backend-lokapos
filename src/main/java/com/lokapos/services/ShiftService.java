package com.lokapos.services;

import com.lokapos.model.request.RequestStartShift;
import com.lokapos.model.response.ResponseDetailShift;
import com.lokapos.model.response.ResponseListShift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShiftService {
    String startShift(RequestStartShift req);

    String closeShift();

    Page<ResponseListShift> staffShifts(Pageable pageable);

    ResponseDetailShift detailShift(String id);
}
