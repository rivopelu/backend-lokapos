package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AuthController;
import com.lokapos.services.AuthService;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public String ping() {
        return authService.ping();
    }
}
