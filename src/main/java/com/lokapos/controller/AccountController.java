package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("account")
public interface AccountController {
    @GetMapping("v1/get-me")
    BaseResponse getMe();
}
