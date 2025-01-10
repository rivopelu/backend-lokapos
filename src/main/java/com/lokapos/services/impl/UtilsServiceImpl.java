package com.lokapos.services.impl;

import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.ResponseUrl;
import com.lokapos.services.UtilsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UtilsServiceImpl implements UtilsService {
    @Override
    public ResponseUrl uploadFile(MultipartFile file) {
        try {
            return ResponseUrl.builder().build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
