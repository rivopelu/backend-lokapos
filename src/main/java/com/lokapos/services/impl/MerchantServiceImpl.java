package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.entities.Merchant;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.ResponseListMerchant;
import com.lokapos.repositories.MerchantRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final AccountService accountService;

    @Override
    public RESPONSE_ENUM createMerchant(RequestCreateMerchant req) {

        Account account = accountService.getCurrentAccount();
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
        Business business = account.getBusiness();

        Merchant merchant = Merchant.builder()
                .merchantName(req.getName())
                .address(req.getAddress())
                .provinceId(req.getProvinceId())
                .cityId(req.getCityId())
                .districtId(req.getDistrictId())
                .subDistrictId(req.getSubDistrictId())
                .business(business)
                .build();
        EntityUtils.created(merchant, account.getId());
        try {
            merchantRepository.save(merchant);
            return RESPONSE_ENUM.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListMerchant> getListMerchant(Pageable pageable) {
        String businessId = accountService.getCurrentBusinessIdOrNull();

        try {
            Page<Merchant> merchantPage = merchantRepository.findByBusinessIdAndActiveIsTrue(pageable, businessId);
            List<ResponseListMerchant> responseListMerchants = new ArrayList<>();

            for (Merchant merchant : merchantPage.getContent()) {
                ResponseListMerchant responseListMerchant = ResponseListMerchant.builder()
                        .name(merchant.getMerchantName())
                        .id(merchant.getId())
                        .build();
                responseListMerchants.add(responseListMerchant);
            }
            return new PageImpl<>(responseListMerchants, pageable, merchantPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
