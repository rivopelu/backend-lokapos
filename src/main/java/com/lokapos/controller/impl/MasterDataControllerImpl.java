package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.MasterDataController;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.request.RequestCreateEditMenu;
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

    @Override
    public BaseResponse getAllCategories() {

        return ResponseHelper.createBaseResponse(masterDataService.getAllCategories());
    }

    @Override
    public BaseResponse createNewMenu(RequestCreateEditMenu req) {

        return ResponseHelper.createBaseResponse(masterDataService.createNewMenu(req));
    }

    @Override
    public BaseResponse getAllMenus() {
        return ResponseHelper.createBaseResponse(masterDataService.getAllMenus());
    }

    @Override
    public BaseResponse editMenu(RequestCreateEditMenu req, String id) {
        return ResponseHelper.createBaseResponse(masterDataService.editMenu(req, id));
    }

    @Override
    public BaseResponse getDetailMenu(String id) {
        return ResponseHelper.createBaseResponse(masterDataService.getDetailMenu(id));
    }

}
