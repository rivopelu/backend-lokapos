package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.request.RequestCreateEditMenu;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseController("master-data")
public interface MasterDataController {

    @PostMapping("v1/category/new")
    BaseResponse createNewCategory(@RequestBody List<RequestCreateEditCategory> req);

    @GetMapping("v1/category/list")
    BaseResponse getAllCategories();

    @PostMapping("v1/menu/new")
    BaseResponse createNewMenu(@RequestBody RequestCreateEditMenu req);


    @GetMapping("v1/menu/list")
    BaseResponse getAllMenus();


    @PutMapping("/v1/menu/edit/{id}")
    BaseResponse editMenu(
            @RequestBody RequestCreateEditMenu req,
            @PathVariable(name = "id") String id
    );

    @GetMapping("v1/menu/detail/{id}")
    BaseResponse getDetailMenu(@PathVariable String id);
}
