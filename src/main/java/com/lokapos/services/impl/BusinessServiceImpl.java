package com.lokapos.services.impl;

import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.services.BusinessService;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Override
    public RESPONSE_ENUM createBusiness(RequestCreateBusiness req) {
        return RESPONSE_ENUM.SUCCESS;
    }

}
