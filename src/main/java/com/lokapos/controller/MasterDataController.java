package com.lokapos.controller;

import com.lokapos.annotations.AdminAccess;
import com.lokapos.annotations.BaseController;
import com.lokapos.annotations.SuperAdminAccess;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@BaseController("master-data")
public interface MasterDataController {

    @PostMapping("v1/category/new")
    BaseResponse createNewCategory(@RequestBody List<RequestCreateEditCategory> req);

    @GetMapping("v1/category/list")
    BaseResponse getAllCategories();
}
