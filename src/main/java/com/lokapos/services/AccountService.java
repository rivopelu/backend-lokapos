package com.lokapos.services;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.response.ResponseBusinessDetail;
import com.lokapos.model.response.ResponseGetMe;
import com.lokapos.model.response.ResponseListAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    ResponseGetMe getMe() throws NotAuthorizedException;

    Account getCurrentAccount();

    String getCurrentAccountId();

    String verifyEmail(ReqOtp req);

    RESPONSE_ENUM resendVerificationEmail();

    ResponseBusinessDetail getBusinessDetail(Business business);


    Page<ResponseListAccount> getListAccountAdmin(Pageable pageable);

    String getCurrentBusinessIdOrNull();
}
