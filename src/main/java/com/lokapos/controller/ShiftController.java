package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestStartShift;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("shift")
public interface ShiftController {

    @PostMapping("v1/start")
    BaseResponse startShift(@RequestBody RequestStartShift req);

    @PatchMapping("v1/close")
    BaseResponse closeShift();


    @GetMapping("v1/staff-list")
    BaseResponse staffShifts(Pageable pageable);

    @GetMapping("v1/detail/{id}")
    BaseResponse detailShift(@PathVariable("id") String id);

}
