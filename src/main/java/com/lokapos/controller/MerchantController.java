package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.entities.Merchant;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("merchant")
public interface MerchantController {

    @PostMapping("v1/new")
    BaseResponse createMerchant(@RequestBody RequestCreateMerchant requestCreateMerchant);
}
