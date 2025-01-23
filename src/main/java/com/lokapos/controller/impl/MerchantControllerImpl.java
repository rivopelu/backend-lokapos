package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.MerchantController;
import com.lokapos.entities.Merchant;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class MerchantControllerImpl implements MerchantController {
    private final MerchantService merchantService;
    @Override
    public BaseResponse createMerchant(RequestCreateMerchant req) {
        return ResponseHelper.createBaseResponse(merchantService.createMerchant(req));
    }

    @Override
    public BaseResponse getListMerchant(Pageable pageable) {
        return ResponseHelper.createBaseResponse(merchantService.getListMerchant(pageable));
    }
}
