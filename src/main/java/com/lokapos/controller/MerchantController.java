package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.annotations.PublicAccess;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("merchant")
public interface MerchantController {

    @GetMapping("v1/top-merchant")
    @PublicAccess
    BaseResponse getTopMerchant();


    @PostMapping("v1/new")
    BaseResponse createMerchant(@RequestBody RequestCreateMerchant requestCreateMerchant);

    @GetMapping("v1/list")
    BaseResponse getListMerchant(Pageable pageable);


    @PatchMapping("v1/select-merchant")
    BaseResponse selectMerchant(@RequestParam(name = "merchant_id") String merchantId);



}
