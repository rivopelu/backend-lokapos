package com.lokapos.services;


import com.lokapos.entities.Merchant;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.ResponseListMerchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MerchantService {
    RESPONSE_ENUM createMerchant(RequestCreateMerchant req);

    Page<ResponseListMerchant> getListMerchant(Pageable pageable);
}
