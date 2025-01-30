package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.UtilsController;
import com.lokapos.model.request.RequestTestPushNotification;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.UtilsService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;
import utils.ResponseHelper;

@RequiredArgsConstructor
@BaseControllerImpl
public class UtilsControllerImpl implements UtilsController {
    private final UtilsService utilsService;

    @Override
    public BaseResponse upload(MultipartFile multipartFile, String folder) throws BadRequestException {
        return ResponseHelper.createBaseResponse(utilsService.uploadFile(multipartFile, folder));
    }

    @Override
    public BaseResponse testNotification(RequestTestPushNotification req) {
        return ResponseHelper.createBaseResponse(utilsService.testNotification(req));
    }
}
