package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.ShiftController;
import com.lokapos.model.request.RequestStartShift;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.ShiftService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class ShiftControllerImpl implements ShiftController {

    private final ShiftService shiftService;

    @Override
    public BaseResponse startShift(RequestStartShift req) {

        return ResponseHelper.createBaseResponse(shiftService.startShift(req));

    }
}
