package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.CategoryMenu;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.model.request.RequestCreateEditCategory;
import com.lokapos.repositories.CategoryMenuRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final AccountService accountService;
    private final CategoryMenuRepository categoryMenuRepository;

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
}
