package com.lokapos.services;

import com.lokapos.entities.Account;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.response.ResponseGetMe;

public interface AccountService {
    ResponseGetMe getMe() throws NotAuthorizedException;

    Account getCurrentAccount();

    String verifyEmail(ReqOtp req);
}
