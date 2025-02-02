package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestTestPushNotification;
import com.lokapos.model.response.BaseResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@BaseController("com/lokapos/utils")
public interface UtilsController {

    @PostMapping("v1/upload")
    BaseResponse upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("folder") String folder) throws BadRequestException;

    @PostMapping("v1/test-notification")
    BaseResponse testNotification(@RequestBody RequestTestPushNotification req);

}
