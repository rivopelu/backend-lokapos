package com.lokapos.services;


import com.lokapos.entities.Business;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateBusiness;

public interface BusinessService {

    RESPONSE_ENUM createBusiness(RequestCreateBusiness req);

}
