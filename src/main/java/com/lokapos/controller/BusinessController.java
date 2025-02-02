package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("business")
public interface BusinessController {

    @PostMapping("v1/new")
    BaseResponse createBusiness(@RequestBody RequestCreateBusiness req);

    @GetMapping("v1/account-business")
    BaseResponse getAccountBusiness();

    @PutMapping("v1/edit/account-business")
    BaseResponse editAccountBusiness(@RequestBody RequestCreateBusiness req);

}
