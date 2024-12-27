package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.BusinessRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final AccountService accountService;
    private final BusinessRepository businessRepository;
    private final AccountRepository accountRepository;

    @Override
    public RESPONSE_ENUM createBusiness(RequestCreateBusiness req) {
        Account account = accountService.getCurrentAccount();

        if (account.getBusiness() != null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_HAVE_BUSINESS.name());
        }

        Business business = Business.builder()
                .businessName(req.getName())
                .address(req.getAddress())
                .provinceId(req.getProvinceId())
                .cityId(req.getCityId())
                .districtId(req.getDistrictId())
                .subDistrictId(req.getSubDistrictId())
                .isActiveSubscription(false)
                .subscriptionExpireDate(0L)
                .logo(req.getLogo())
                .build();
        EntityUtils.created(business, account.getId());
        try {

            business = businessRepository.save(business);
            account.setBusiness(business);
            accountRepository.save(account);
            return RESPONSE_ENUM.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

}
