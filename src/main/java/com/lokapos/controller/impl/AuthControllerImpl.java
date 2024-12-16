package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AuthController;

@BaseControllerImpl
public class AuthControllerImpl implements AuthController {
    @Override
    public String ping() {
        return "PONG";
    }
}
