package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@BaseController("area")
public interface AreaController {

    @GetMapping("/v1/province")
    BaseResponse getProvince();

    @GetMapping("/v1/city/{provinceId}")
    BaseResponse getCity(@PathVariable("provinceId") BigInteger provinceId);

    @GetMapping("v1/district/{cityId}")
    BaseResponse getDistrict(@PathVariable("cityId") BigInteger cityId);

    @GetMapping("v1/sub-district/{districtId}")
    BaseResponse getSubDistrict(@PathVariable("districtId") BigInteger districtId);

    @GetMapping("v1/get-area-sub-district")
    BaseResponse getAreaSubDistrict(@RequestParam("q") String q);

}
