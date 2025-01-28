package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.entities.Shift;
import com.lokapos.model.request.RequestStartShift;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("shift")
public interface ShiftController {

    @PostMapping("v1/start")
    BaseResponse startShift(@RequestBody RequestStartShift req);

    @PatchMapping("v1/close")
    BaseResponse closeShift();

}
