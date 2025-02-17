package com.lokapos.services;

import com.lokapos.model.request.RequestSignIn;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.ResponseCheckEmailAddress;
import com.lokapos.model.response.ResponseSignIn;

public interface AuthService {
    String ping();

    String signUp(RequestSignUp req);

    ResponseSignIn signIn(RequestSignIn req);

    ResponseSignIn posSignIn(RequestSignIn req);

    ResponseSignIn userSignUp(RequestSignUp req);

    ResponseCheckEmailAddress checkAvailableEmail(String email);
}
