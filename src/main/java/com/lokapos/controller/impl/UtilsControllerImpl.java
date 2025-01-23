package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.UtilsController;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import utils.ResponseHelper;

@RequiredArgsConstructor
@BaseControllerImpl
public class UtilsControllerImpl implements UtilsController {
    private final UtilsService utilsService;
    @Override
    public BaseResponse upload(MultipartFile multipartFile) {
        return ResponseHelper.createBaseResponse(utilsService.uploadFile(multipartFile));
    }
}
