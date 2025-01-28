package com.lokapos.services;

import com.lokapos.model.request.RequestStartShift;

public interface ShiftService {
    String startShift(RequestStartShift req);

    String closeShift();
}
