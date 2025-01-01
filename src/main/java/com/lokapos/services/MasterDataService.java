package com.lokapos.services;

import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.request.RequestCreateMenu;
import com.lokapos.model.response.ResponseCategoryList;
import com.lokapos.model.response.ResponseListMenu;

import java.util.List;

public interface MasterDataService {

    String createNewCategory(List<RequestCreateEditCategory> req);

    List<ResponseCategoryList> getAllCategories();

    String createNewMenu(RequestCreateMenu req);

    List<ResponseListMenu> getAllMenus();
}
