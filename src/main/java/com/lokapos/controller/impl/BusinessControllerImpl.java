package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.BusinessController;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.BusinessService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class BusinessControllerImpl implements BusinessController {

    private final BusinessService businessService;

    @Override
    public BaseResponse createBusiness(RequestCreateBusiness req) {
        return ResponseHelper.createBaseResponse(businessService.createBusiness(req));
    }

    @Override
    public BaseResponse getAccountBusiness() {

        return ResponseHelper.createBaseResponse(businessService.getAccountBusiness());
    }

    @Override
    public BaseResponse editAccountBusiness(RequestCreateBusiness req) {
        return ResponseHelper.createBaseResponse(businessService.editAccountBusiness(req));
    }


}
