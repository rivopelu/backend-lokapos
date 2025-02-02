package com.lokapos.services;


import com.lokapos.entities.Business;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.model.request.RequestTopUp;
import com.lokapos.model.response.ResponseDetailBusiness;

public interface BusinessService {

    RESPONSE_ENUM createBusiness(RequestCreateBusiness req);

    ResponseDetailBusiness getAccountBusiness();

    String editAccountBusiness(RequestCreateBusiness req);

    String createWallet();

    Long getWalletBalance();

    String topUp(RequestTopUp req);
}
