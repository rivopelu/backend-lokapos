package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@BaseController("utils")
public interface UtilsController {

    @PostMapping("v1/upload")
    BaseResponse upload(@RequestParam("file") MultipartFile multipartFile);

}
