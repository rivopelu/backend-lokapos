package com.lokapos.services.impl;

import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.services.MasterDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataServiceImpl implements MasterDataService {
    @Override
    public String createNewCategory(List<RequestCreateEditCategory> req) {
        return "";
    }
}
