package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestSignIn;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@BaseController("auth")
public interface AuthController {

    @GetMapping("ping")
    BaseResponse ping();

    @PostMapping("v1/sign-up")
    BaseResponse signUp(@RequestBody RequestSignUp req);

    @PostMapping("v1/user/sign-up")
    BaseResponse userSignUp(@RequestBody RequestSignUp req);

    @GetMapping("test-mail")
    BaseResponse testMail();

    @PostMapping("v1/sign-in")
    BaseResponse signIn(@RequestBody RequestSignIn req);

    @PostMapping("v1/pos/sign-in")
    BaseResponse posSignIn(@RequestBody RequestSignIn req);

    @GetMapping("v1/check-available-email")
    BaseResponse checkAvailableEmail(@RequestParam String email);

}
