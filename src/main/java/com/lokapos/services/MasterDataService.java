package com.lokapos.services;

import com.lokapos.model.request.RequestCreateEditCategory;

import java.util.List;

public interface MasterDataService {

    String createNewCategory(List<RequestCreateEditCategory> req);

}
