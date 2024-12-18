package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AccountController;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.AccountService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public BaseResponse getMe() {
        return ResponseHelper.createBaseResponse(accountService.getMe());
    }
}
