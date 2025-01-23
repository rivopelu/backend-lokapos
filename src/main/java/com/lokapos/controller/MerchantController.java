package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.entities.Merchant;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("merchant")
public interface MerchantController {

    @PostMapping("v1/new")
    BaseResponse createMerchant(@RequestBody RequestCreateMerchant requestCreateMerchant);

    @GetMapping("v1/list")
    BaseResponse getListMerchant(Pageable pageable);

}
