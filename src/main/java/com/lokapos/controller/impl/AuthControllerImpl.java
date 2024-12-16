package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AuthController;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.AuthService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse(authService.ping());
    }
}
