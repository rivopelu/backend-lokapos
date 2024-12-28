package com.lokapos.services;


import com.lokapos.entities.Merchant;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateMerchant;

public interface MerchantService {
    RESPONSE_ENUM createMerchant(RequestCreateMerchant req);
}
