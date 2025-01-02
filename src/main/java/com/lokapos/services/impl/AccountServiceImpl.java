package com.lokapos.services.impl;

import com.lokapos.constants.AuthConstant;
import com.lokapos.entities.*;
import com.lokapos.enums.OTP_AND_TOKEN_TYPE_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.ReqOtp;
import com.lokapos.model.request.RequestCreateAccount;
import com.lokapos.model.response.ResponseBusinessDetail;
import com.lokapos.model.response.ResponseCreateAccount;
import com.lokapos.model.response.ResponseGetMe;
import com.lokapos.model.response.ResponseListAccount;
import com.lokapos.repositories.*;
import com.lokapos.services.AccountService;
import com.lokapos.services.AreaService;
import com.lokapos.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utils.EntityUtils;
import utils.UtilsHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final HttpServletRequest httpServletRequest;
    private final OtpAndTokenRepository otpAndTokenRepository;
    private final EmailService emailService;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubDistrictRepository subDistrictRepository;
    private final AreaService areaService;
    private final BusinessRepository businessRepository;

    @Override
    public ResponseGetMe getMe() throws NotAuthorizedException {
        try {
            ResponseBusinessDetail responseBusinessDetail = null;
            Account account = getCurrentAccount();
            if (account == null) {
                throw new NotAuthorizedException(RESPONSE_ENUM.NOT_AUTHORIZED.name());
            }
            if (account.getBusiness() != null) {
                responseBusinessDetail = getBusinessDetail(account.getBusiness());
            }


            return ResponseGetMe.builder()
                    .fullName(account.getFirstName() + " " + account.getLastName())
                    .firstName(account.getFirstName())
                    .isVerifiedEmail(account.getIsVerifiedEmail())
                    .lastName(account.getLastName())
                    .email(account.getEmail())
                    .id(account.getId())
                    .business(responseBusinessDetail)
                    .build();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Account getCurrentAccount() {
        String currentUserId = httpServletRequest.getAttribute(AuthConstant.HEADER_X_WHO).toString();
        Optional<Account> account = accountRepository.findById(currentUserId);
        return account.orElse(null);
    }

    @Override
    public String getCurrentAccountId() {
        return getCurrentAccount().getId();
    }

    @Override
    public String verifyEmail(ReqOtp req) {
        Account account = getCurrentAccount();
        Optional<OtpAndToken> findOtp = otpAndTokenRepository.queryFindOtp(req.getOtp(), account.getId());
        if (findOtp.isEmpty()) {
            throw new BadRequestException(RESPONSE_ENUM.OTP_INVALID.name());
        }
        OtpAndToken otp = findOtp.get();
        if (otp.getExpireDate() < System.currentTimeMillis()) {
            throw new BadRequestException(RESPONSE_ENUM.OTP_EXPIRED.name());
        }
        account.setIsVerifiedEmail(true);
        accountRepository.save(account);
        otp.setActive(false);
        otpAndTokenRepository.save(otp);
        try {
            return "SUCCESS";
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public RESPONSE_ENUM resendVerificationEmail() {
        Account account = getCurrentAccount();
        OtpAndToken otp = otpAndTokenRepository.findByAccountIdAndActiveIsTrueAndType(account.getId(), OTP_AND_TOKEN_TYPE_ENUM.SIGN_UP_OTP).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.NOT_FOUND_OTP.name()));
        String generateOtp = UtilsHelper.generateNumericOTP();
        otp.setOtp(generateOtp);
        Long generateExpire = UtilsHelper.getExpireOnMinutes(3);
        otp.setExpireDate(generateExpire);

        otpAndTokenRepository.save(otp);
        emailService.SendingOtpSignUp(generateOtp, account);
        try {
            return RESPONSE_ENUM.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }

    }

    @Override
    public ResponseBusinessDetail getBusinessDetail(Business business) {

        Province province = provinceRepository.findById(business.getProvinceId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.PROVINCE_NOT_FOUND.name()));
        City city = cityRepository.findById(business.getCityId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.CITY_NOT_FOUND.name()));
        District district = districtRepository.findById(business.getDistrictId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.DISTRICT_NOT_FOUND.name()));
        SubDistrict subDistrict = subDistrictRepository.findById(business.getSubDistrictId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.SUB_DISTRICT_NOT_FOUND.name()));

        String fullAddress = areaService.getFullAddress(province, city, district, subDistrict);

        try {
            return ResponseBusinessDetail.builder()
                    .businessName(business.getBusinessName())
                    .businessId(business.getId())
                    .businessLogo(business.getLogo())
                    .expireDate(business.getSubscriptionExpireDate())
                    .isActiveSubscription(business.getIsActiveSubscription())
                    .businessFullAddress(fullAddress)
                    .provinceName(province.getName())
                    .cityName(city.getName())
                    .districtName(district.getName())
                    .subDistrictName(subDistrict.getName())
                    .provinceId(province.getId())
                    .cityId(city.getId())
                    .districtId(district.getId())
                    .subDistrictId(subDistrict.getId())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListAccount> getListAccountAdmin(Pageable pageable) {
        Long totalData = 0L;
        String businessId = getCurrentBusinessIdOrNull();
        List<ResponseListAccount> responseListAccounts = new ArrayList<>();

        if (businessId != null) {
            Page<Account> accountPage = accountRepository.findAllByBusinessIdAndActiveIsTrueOrderByCreatedDateDesc(pageable, businessId);
            for (Account account : accountPage.getContent()) {
                ResponseListAccount responseListAccount = ResponseListAccount.builder()
                        .firstName(account.getFirstName())
                        .lastName(account.getLastName())
                        .avatar(account.getAvatar())
                        .createdDate(account.getCreatedDate())
                        .fullName(account.getFirstName() + " " + account.getLastName())
                        .role(account.getRole())
                        .email(account.getEmail())
                        .build();
                responseListAccounts.add(responseListAccount);
            }
            totalData = accountPage.getTotalElements();
        }
        try {

            return new PageImpl<>(responseListAccounts, pageable, totalData);

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String getCurrentBusinessIdOrNull() {
        String businessId = null;
        try {
            Account account = getCurrentAccount();
            if (account.getBusiness() != null) {
                businessId = account.getBusiness().getId();
            }
            return businessId;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseCreateAccount createAccount(RequestCreateAccount req) {
        Account account = getCurrentAccount();
        String password = UtilsHelper.generateRandomPassword(8);

        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }

        String encodedPassword = passwordEncoder.encode(password);
        Account builderAccount = Account.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .isVerifiedEmail(true)
                .role(req.getRole())
                .email(req.getEmail())
                .business(account.getBusiness())
                .avatar(UtilsHelper.generateAvatar(req.getFirstName() + " " + req.getLastName()))
                .password(encodedPassword)
                .build();

        EntityUtils.created(builderAccount, account.getId());
        Account newAccount = accountRepository.save(builderAccount);
        emailService.sendNewAccountRegistered(newAccount, password);

        try {
            return ResponseCreateAccount
                    .builder()
                    .id(builderAccount.getId())
                    .firstName(builderAccount.getFirstName())
                    .lastName(builderAccount.getLastName())
                    .avatar(builderAccount.getAvatar())
                    .email(builderAccount.getEmail())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }


}
