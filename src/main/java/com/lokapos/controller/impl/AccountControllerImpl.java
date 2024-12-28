package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AccountController;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public BaseResponse getMe() {
        return ResponseHelper.createBaseResponse(accountService.getMe());
    }

    @Override
    public BaseResponse verifyEmail(ReqOtp req) {
        return ResponseHelper.createBaseResponse(accountService.verifyEmail(req));
    }

    @Override
    public BaseResponse resendVerificationEmail() {
        return ResponseHelper.createBaseResponse(accountService.resendVerificationEmail());
    }

    @Override
    public BaseResponse getListAccountAdmin(Pageable pageable) {

        return  ResponseHelper.createBaseResponse(accountService.getListAccountAdmin(pageable));
    }
}
