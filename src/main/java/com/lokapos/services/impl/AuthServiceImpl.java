package com.lokapos.services.impl;

import com.lokapos.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String ping() {
        return "PONG";
    }
}
