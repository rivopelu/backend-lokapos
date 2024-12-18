package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@BaseController("account")
public interface AccountController {

    @GetMapping("v1/get-me")
    BaseResponse getMe();

    @PostMapping("v1/verify-email")
    BaseResponse verifyEmail(@RequestBody() ReqOtp req);


}
