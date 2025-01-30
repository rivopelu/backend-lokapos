package com.lokapos.services;

import com.lokapos.model.request.RequestTestPushNotification;
import com.lokapos.model.response.ResponseUrl;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

public interface UtilsService {

    ResponseUrl uploadFile(MultipartFile file, String folder) throws BadRequestException;

    String testNotification(RequestTestPushNotification req);
}
