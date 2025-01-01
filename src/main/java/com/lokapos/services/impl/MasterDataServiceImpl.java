package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.CategoryMenu;
import com.lokapos.entities.ServingMenu;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.model.request.RequestCreateMenu;
import com.lokapos.model.response.ResponseCategoryList;
import com.lokapos.model.response.ResponseListMenu;
import com.lokapos.repositories.CategoryMenuRepository;
import com.lokapos.repositories.ServingMenuRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final AccountService accountService;
    private final CategoryMenuRepository categoryMenuRepository;
    private final ServingMenuRepository servingMenuRepository;

    @Override
    public String createNewCategory(List<RequestCreateEditCategory> req) {
        Account account = accountService.getCurrentAccount();
        int index = 0;

        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }

        List<CategoryMenu> categoryMenuList = new ArrayList<>();
        for (RequestCreateEditCategory reqCat : req) {
            CategoryMenu categoryMenu = CategoryMenu.builder()
                    .name(reqCat.getName())
                    .count(index)
                    .business(account.getBusiness())
                    .build();
            EntityUtils.created(categoryMenu, account.getId());
            index = index + 1;
            categoryMenuList.add(categoryMenu);
        }

        categoryMenuRepository.saveAll(categoryMenuList);
        return RESPONSE_ENUM.SUCCESS.name();
    }

    @Override
    public List<ResponseCategoryList> getAllCategories() {
        String businessId = accountService.getCurrentBusinessIdOrNull();
        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAllByBusinessIdAndActiveIsTrueOrderByCountDesc(businessId);
        List<ResponseCategoryList> responseCategoryList = new ArrayList<>();

        for (CategoryMenu categoryMenu : categoryMenuList) {
            ResponseCategoryList response = ResponseCategoryList.builder()
                    .name(categoryMenu.getName())
                    .id(categoryMenu.getId())
                    .build();
            responseCategoryList.add(response);
        }


        try {
            return responseCategoryList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String createNewMenu(RequestCreateMenu req) {
        CategoryMenu categoryMenu = categoryMenuRepository.findById(req.getCategoryId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.CATEGORY_NOT_FOUND.name()));
        Account account = accountService.getCurrentAccount();
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }

        if (!Objects.equals(categoryMenu.getBusiness().getId(), account.getBusiness().getId())) {
            throw new BadRequestException(RESPONSE_ENUM.CATEGORY_AND_BUSINESS_NOT_MATCH.name());
        }
        try {
            ServingMenu servingMenu = ServingMenu.builder()
                    .name(req.getName())
                    .description(req.getDescription())
                    .price(req.getPrice())
                    .categoryMenu(categoryMenu)
                    .business(account.getBusiness())
                    .image(req.getImageUrl())
                    .build();
            EntityUtils.created(servingMenu, account.getId());
            servingMenuRepository.save(servingMenu);

            return RESPONSE_ENUM.SUCCESS.name();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseListMenu> getAllMenus() {
        String businessId = accountService.getCurrentBusinessIdOrNull();
        List<ServingMenu> servingMenuList = servingMenuRepository.findAllByBusinessIdAndActiveIsTrueOrderByCreatedDateDesc(businessId);
        List<ResponseListMenu> responseList = new ArrayList<>();
        try {
            for (ServingMenu servingMenu : servingMenuList) {
                ResponseListMenu response = ResponseListMenu.builder()
                        .id(servingMenu.getId())
                        .name(servingMenu.getName())
                        .description(servingMenu.getDescription())
                        .categoryId(servingMenu.getCategoryMenu().getId())
                        .categoryName(servingMenu.getCategoryMenu().getName())
                        .image(servingMenu.getImage())
                        .price(servingMenu.getPrice())
                        .createdDate(servingMenu.getCreatedDate())
                        .build();
                responseList.add(response);
            }
            return responseList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
