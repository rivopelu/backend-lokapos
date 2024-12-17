package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("auth")
public interface AuthController {

    @GetMapping("ping")
    BaseResponse ping();

    @PostMapping("sign-up")
    BaseResponse signUp(@RequestBody RequestSignUp req);

    @GetMapping("test-mail")
    BaseResponse testMail();

}
