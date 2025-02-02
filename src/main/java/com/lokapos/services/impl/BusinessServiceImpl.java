package com.lokapos.services.impl;

import com.lokapos.constants.FlagConstants;
import com.lokapos.entities.*;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_STATUS_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_TYPE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.ReqPaymentObject;
import com.lokapos.model.request.RequestCreateBusiness;
import com.lokapos.model.request.RequestTopUp;
import com.lokapos.model.response.ResponseDetailBusiness;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.BusinessRepository;
import com.lokapos.repositories.WalletRepository;
import com.lokapos.repositories.WalletTransactionRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.AreaService;
import com.lokapos.services.BusinessService;
import com.lokapos.services.PaymentService;
import com.lokapos.utils.UtilsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lokapos.utils.EntityUtils;
import com.lokapos.utils.PaymentRequestUtils;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final AccountService accountService;
    private final BusinessRepository businessRepository;
    private final AccountRepository accountRepository;
    private final AreaService areaService;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final PaymentService paymentService;

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
                .subscriptionExpireDate(null)
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

    @Override
    public ResponseDetailBusiness getAccountBusiness() {
        Business business = findAccountBusiness();

        Province province = areaService.getProvinceById(business.getProvinceId());
        City city = areaService.getCityById(business.getCityId());
        District district = areaService.getDistrictById(business.getDistrictId());
        SubDistrict subDistrict = areaService.getSubDistrictById(business.getSubDistrictId());
        try {
            return ResponseDetailBusiness.builder()
                    .id(business.getId())
                    .logo(business.getLogo())
                    .name(business.getBusinessName())
                    .address(business.getAddress())
                    .provinceId(province.getId())
                    .provinceName(province.getName())
                    .cityId(city.getId())
                    .cityName(city.getName())
                    .districtId(district.getId())
                    .districtName(district.getName())
                    .subDistrictId(subDistrict.getId())
                    .subDistrictName(subDistrict.getName())
                    .build();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String editAccountBusiness(RequestCreateBusiness req) {
        Account getCurrentAccount = accountService.getCurrentAccount();
        Business business = findAccountBusiness(getCurrentAccount);
        business.setBusinessName(req.getName());
        business.setAddress(req.getAddress());
        business.setProvinceId(req.getProvinceId());
        business.setCityId(req.getCityId());
        business.setDistrictId(req.getDistrictId());
        business.setSubDistrictId(req.getSubDistrictId());
        business.setLogo(req.getLogo());

        try {

            EntityUtils.updated(business, getCurrentAccount.getId());
            businessRepository.save(business);
            return RESPONSE_ENUM.SUCCESS.toString();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String createWallet() {
        Account account = accountService.getCurrentAccount();
        Business business = findAccountBusiness(account);
        if (business.getWallet() != null) {
            throw new BadRequestException(RESPONSE_ENUM.WALLET_ALREADY_EXIST.name());
        }
        Wallet wallet = Wallet.builder().balance(0L).build();
        EntityUtils.created(wallet, account.getId());
        try {
            wallet = walletRepository.save(wallet);
            business.setWallet(wallet);
            businessRepository.save(business);
            return RESPONSE_ENUM.SUCCESS.toString();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Long getWalletBalance() {
        Business business = findAccountBusiness();
        if (business.getWallet() == null) {
            throw new BadRequestException(RESPONSE_ENUM.WALLET_NOT_FOUND.name());
        }
        try {
            return business.getWallet().getBalance();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String topUp(RequestTopUp req) {
        Account getCurrentAccount = accountService.getCurrentAccount();
        Business business = findAccountBusiness(getCurrentAccount);
        Wallet wallet = getBussinessWallet(business);

        String id = UtilsHelper.generateIdFlag(FlagConstants.topUp);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .amount(req.getAmount())
                .business(business)
                .wallet(wallet)
                .type(WALLET_TRANSACTION_TYPE_ENUM.TOP_UP)
                .status(WALLET_TRANSACTION_STATUS_ENUM.PENDING)
                .paymentMethod(req.getBankName())
                .build();
        walletTransaction.setId(id);
        EntityUtils.created(walletTransaction, getCurrentAccount.getId());

        try {
            walletTransactionRepository.save(walletTransaction);
            return paymentService.createPaymentTopup(buildPaymentObjectTopUp(walletTransaction));
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private Wallet getBussinessWallet(Business business) {
        if (business.getWallet() == null) {
            throw new BadRequestException(RESPONSE_ENUM.WALLET_NOT_FOUND.name());
        }
        return business.getWallet();
    }

    private Business findAccountBusiness() {
        Account account = accountService.getCurrentAccount();
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
        return account.getBusiness();
    }

    private Business findAccountBusiness(Account account) {
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
        return account.getBusiness();
    }


    private ReqPaymentObject buildPaymentObjectTopUp(WalletTransaction walletTransaction) {
        ReqPaymentObject.TransactionDetail buildTransactionDetail = ReqPaymentObject.TransactionDetail
                .builder()
                .orderId(walletTransaction.getId())
                .grossAmount(BigInteger.valueOf(walletTransaction.getAmount()))
                .build();

        String parseBankName = PaymentRequestUtils.parsePaymentMethod(walletTransaction.getPaymentMethod());

        ReqPaymentObject.BankTransfer bankTransfer = ReqPaymentObject.BankTransfer.builder()
                .bankName(parseBankName)
                .build();

        return ReqPaymentObject.builder()
                .transactionDetail(buildTransactionDetail)
                .bankTransfer(bankTransfer)
                .build();
    }

}
