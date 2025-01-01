package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.MasterDataController;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.MasterDataService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

import java.util.List;

@RequiredArgsConstructor
@BaseControllerImpl
public class MasterDataControllerImpl implements MasterDataController {

    private final MasterDataService masterDataService;

    @Override
    public BaseResponse createNewCategory(List<RequestCreateEditCategory> req) {
        return ResponseHelper.createBaseResponse(masterDataService.createNewCategory(req));
    }
}
