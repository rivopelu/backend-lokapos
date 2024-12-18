package com.lokapos.services;

import com.lokapos.entities.Account;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.model.response.ResponseGetMe;

public interface AccountService {
    ResponseGetMe getMe() throws NotAuthorizedException;

    Account getCurrentAccount();
}
