package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.request.RequestCreateAccount;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("account")
public interface AccountController {

    @GetMapping("v1/get-me")
    BaseResponse getMe();

    @PostMapping("v1/verify-email")
    BaseResponse verifyEmail(@RequestBody() ReqOtp req);

    @PatchMapping("v1/resend-verification-email")
    BaseResponse resendVerificationEmail();

    @GetMapping("v1/admin/account-list")
    BaseResponse getListAccountAdmin(Pageable pageable);

    @PostMapping("v1/admin/create-account")
    BaseResponse createAccount(@RequestBody RequestCreateAccount req);

}
