package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("auth")
public interface AuthController {

    @GetMapping("ping")
    String ping();
}
