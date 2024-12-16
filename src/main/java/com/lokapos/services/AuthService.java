package com.lokapos.services;

import com.lokapos.model.request.RequestSignUp;

public interface AuthService {
    String ping();

    String signUp(RequestSignUp req);
}
