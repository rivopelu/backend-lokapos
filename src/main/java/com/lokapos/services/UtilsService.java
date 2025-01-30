package com.lokapos.services;

import com.lokapos.model.response.ResponseUrl;
import org.springframework.web.multipart.MultipartFile;

public interface UtilsService {

    ResponseUrl uploadFile(MultipartFile file, String folder);
}
