package com.lokapos.controller.impl;


import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AreaController;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.AreaService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

import java.math.BigInteger;

@BaseControllerImpl
@RequiredArgsConstructor

public class AreaControllerImpl implements AreaController {

    private final AreaService areaService;

    @Override
    public BaseResponse getProvince() {
        return ResponseHelper.createBaseResponse(areaService.getProvince());
    }

    @Override
    public BaseResponse getCity(BigInteger provinceId) {
        return ResponseHelper.createBaseResponse(areaService.getCity(provinceId));
    }

    @Override
    public BaseResponse getDistrict(BigInteger cityId) {
        return ResponseHelper.createBaseResponse(areaService.getDistrict(cityId));
    }

    @Override
    public BaseResponse getSubDistrict(BigInteger districtId) {
        return ResponseHelper.createBaseResponse(areaService.getSubDistrict(districtId));
    }

    @Override
    public BaseResponse getAreaSubDistrict(String q) {
        return ResponseHelper.createBaseResponse(areaService.getAreaSubDistrict(q));
    }

   
}


