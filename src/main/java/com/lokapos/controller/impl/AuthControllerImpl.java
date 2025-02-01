package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.AuthController;
import com.lokapos.model.request.RequestSignIn;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.AuthService;
import com.lokapos.services.EmailService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@BaseControllerImpl
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    public AuthControllerImpl(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse(authService.ping());
    }

    @Override
    public BaseResponse signUp(RequestSignUp req) {
        return ResponseHelper.createBaseResponse(authService.signUp(req));
    }

    @Override
    public BaseResponse userSignUp(RequestSignUp req) {
        return ResponseHelper.createBaseResponse(authService.userSignUp(req));
    }

    @Override
    public BaseResponse testMail() {
        emailService.testingSendMail();
        return ResponseHelper.createBaseResponse("SUCCESS");
    }

    @Override
    public BaseResponse signIn(RequestSignIn req) {
        return ResponseHelper.createBaseResponse(authService.signIn(req));
    }

    @Override
    public BaseResponse posSignIn(RequestSignIn req) {

        return ResponseHelper.createBaseResponse(authService.posSignIn(req));
    }
}
