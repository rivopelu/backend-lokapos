package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("auth")
public interface AuthController {

    @GetMapping("ping")
    BaseResponse ping();
}
