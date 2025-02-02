package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.entities.Merchant;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateMerchant;
import com.lokapos.model.response.ResponseListMerchant;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.MerchantRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lokapos.utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public RESPONSE_ENUM createMerchant(RequestCreateMerchant req) {

        Account account = accountService.getCurrentAccount();
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
        Business business = account.getBusiness();

        Merchant merchant = Merchant.builder().merchantName(req.getName()).address(req.getAddress()).provinceId(req.getProvinceId()).cityId(req.getCityId()).districtId(req.getDistrictId()).logo(req.getLogo()).subDistrictId(req.getSubDistrictId()).business(business).build();
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
            List<ResponseListMerchant> responseListMerchants = buildListMerchants(merchantPage.getContent());
            return new PageImpl<>(responseListMerchants, pageable, merchantPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String selectMerchant(String merchantId) {
        Account account = accountService.getCurrentAccount();
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.MERCHANT_NOT_FOUND.name()));
        try {
            account.setMerchant(merchant);
            accountRepository.save(account);
            return RESPONSE_ENUM.SUCCESS.name();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseListMerchant> getTopMerchant() {
        List<Merchant> merchantList = merchantRepository.getTopMerchant();
        try {
            return buildListMerchants(merchantList);
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private List<ResponseListMerchant> buildListMerchants(List<Merchant> merchantList) {
        List<ResponseListMerchant> responseListMerchants = new ArrayList<>();
        for (Merchant merchant : merchantList) {
            ResponseListMerchant res = ResponseListMerchant.builder().logo(merchant.getLogo()).id(merchant.getId()).name(merchant.getMerchantName()).build();
            responseListMerchants.add(res);
        }
        return responseListMerchants;
    }
}
