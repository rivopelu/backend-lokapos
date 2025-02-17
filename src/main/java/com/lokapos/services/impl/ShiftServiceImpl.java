package com.lokapos.services.impl;

import com.lokapos.entities.*;
import com.lokapos.enums.NOTIFICATION_TYPE_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.USER_ROLE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestStartShift;
import com.lokapos.model.response.ResponseDetailShift;
import com.lokapos.model.response.ResponseListShift;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.NotificationRepository;
import com.lokapos.repositories.ShiftAccountRepository;
import com.lokapos.repositories.ShiftRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.AreaService;
import com.lokapos.services.NotificationService;
import com.lokapos.services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lokapos.utils.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ShiftRepository shiftRepository;
    private final ShiftAccountRepository shiftAccountRepository;
    private final AreaService areaService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public String startShift(RequestStartShift req) {

        if (req.getAccountIds().isEmpty()){
            throw new BadRequestException("Account IDs cannot be empty");
        }

        Account currentAccount = accountService.getCurrentAccount();
        List<Account> accountList = accountRepository.findAllById(req.getAccountIds());

        List<Account> adminAccountList = accountRepository.findByBusinessIdAndActiveIsTrueAndRole(currentAccount.getBusiness().getId(), USER_ROLE_ENUM.ADMIN);


        List<ShiftAccount> shiftAccountList = new ArrayList<>();

        if (currentAccount.getMerchant() == null) {
            throw new BadRequestException(RESPONSE_ENUM.MERCHANT_NOT_FOUND.name());
        }


        Shift shift = Shift.builder()
                .startDate(new Date().getTime())
                .isActive(true)
                .business(currentAccount.getBusiness())
                .merchant(currentAccount.getMerchant())
                .build();
        EntityUtils.created(shift, currentAccount.getId());

        if (accountList.size() != req.getAccountIds().size()) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_ID_NOT_MATCH.name());
        }

        for (Account account : accountList) {
            if (account.getActiveShift() != null) {
                throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_SHIFT_ALREADY_ACTIVE.name());
            }
        }

        shiftRepository.save(shift);

        List<Notification> notificationList = new ArrayList<>();
        for (Account account : adminAccountList) {
            Business business = account.getBusiness();
            if (account.getFcmToken() != null) {
                System.out.println(account.getEmail());
                notificationList.add(
                        Notification.builder()
                                .title("shift_started_title")
                                .body("shift_started_body")
                                .type(NOTIFICATION_TYPE_ENUM.START_SHIFT)
                                .account(account)
                                .token(account.getFcmToken())
                                .business(business)
                                .build()
                );
            }
        }
        if (!notificationList.isEmpty()) {
            notificationList = notificationRepository.saveAll(notificationList);
            notificationService.pushNotificationList(notificationList);
        }

        for (Account account : accountList) {
            account.setActiveShift(shift);
            ShiftAccount shiftAccount = ShiftAccount.builder()
                    .shift(shift)
                    .account(account)
                    .build();

            account.setActiveShift(shift);

            if (account.getMerchant() == null) {
                account.setMerchant(currentAccount.getMerchant());
            }

            shiftAccountList.add(shiftAccount);
        }


        try {

            shiftAccountRepository.saveAll(shiftAccountList);
            accountRepository.saveAll(accountList);
            return RESPONSE_ENUM.SUCCESS.name();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String closeShift() {
        Account currentAccount = accountService.getCurrentAccount();
        List<Account> listShiftAccount;

        if (currentAccount.getActiveShift() == null) {
            throw new BadRequestException(RESPONSE_ENUM.NOT_ACTIVE_SHIFT.name());
        }



        Shift shift = currentAccount.getActiveShift();

        listShiftAccount = accountRepository.findByActiveShiftId(shift.getId());

        for (Account account : listShiftAccount) {
            account.setActiveShift(null);
        }

        List<Account> adminAccountList = accountRepository.findByBusinessIdAndActiveIsTrueAndRole(currentAccount.getBusiness().getId(), USER_ROLE_ENUM.ADMIN);
        List<Notification> notificationList = new ArrayList<>();
        for (Account account : adminAccountList) {
            Business business = account.getBusiness();
            if (account.getFcmToken() != null) {
                System.out.println(account.getEmail());
                notificationList.add(
                        Notification.builder()
                                .title("shift_stop_title")
                                .body("shift_stop_body")
                                .type(NOTIFICATION_TYPE_ENUM.STOP_SHIFT)
                                .account(account)
                                .token(account.getFcmToken())
                                .business(business)
                                .build()
                );
            }
        }
        if (!notificationList.isEmpty()) {
            notificationList = notificationRepository.saveAll(notificationList);
            notificationService.pushNotificationList(notificationList);
        }
        try {

            shift.setIsActive(false);
            shift.setEndDate(new Date().getTime());
            shiftRepository.save(shift);

            accountRepository.saveAll(listShiftAccount);


            return RESPONSE_ENUM.SUCCESS.name();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListShift> staffShifts(Pageable pageable) {
        Account currentAccount = accountService.getCurrentAccount();

        Page<Shift> shiftPage = shiftRepository.getListStaffShift(currentAccount.getId(), pageable);

        try {
            List<ResponseListShift> responseListShiftList = buildShiftList(shiftPage.getContent());
            return new PageImpl<>(responseListShiftList, pageable, shiftPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseDetailShift detailShift(String id) {
        Shift shift = shiftRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.SHIFT_NOT_FOUND.name()));
        List<ResponseDetailShift.accountList> accountList = new ArrayList<>();
        try {

            for (ShiftAccount shiftAccount : shift.getShiftAccounts()) {
                Account account = shiftAccount.getAccount();
                ResponseDetailShift.accountList responseAccount = ResponseDetailShift.accountList.builder()
                        .avatar(account.getAvatar())
                        .id(account.getId())
                        .name(account.getFirstName() + " " + account.getLastName())
                        .email(account.getEmail())
                        .build();
                accountList.add(responseAccount);
            }

            return ResponseDetailShift.builder()
                    .id(shift.getId())
                    .startDate(shift.getStartDate())
                    .endDate(shift.getEndDate())
                    .isActive(shift.getIsActive())
                    .account(accountList)
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListShift> adminShiftList(Pageable pageable) {
        String businessId = accountService.getCurrentBusinessIdOrNull();
        if (businessId == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }


        try {

            Page<Shift> shiftPage = shiftRepository.getShiftByBusinessId(businessId, pageable);
            List<ResponseListShift> responseListShiftList = buildShiftList(shiftPage.getContent());

            return new PageImpl<>(responseListShiftList, pageable, shiftPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private List<ResponseListShift> buildShiftList(List<Shift> shiftList) {
        List<ResponseListShift> responseListShiftList = new ArrayList<>();
        for (Shift shift : shiftList) {
            ResponseListShift response = ResponseListShift.builder()
                    .id(shift.getId())
                    .startDate(shift.getStartDate())
                    .endDate(shift.getEndDate())
                    .merchantName(shift.getMerchant().getMerchantName())
                    .isActive(shift.getIsActive())
                    .build();

            List<ResponseListShift.accountList> respnoseAccountList = new ArrayList<>();
            List<ShiftAccount> shiftAccountList = new ArrayList<>(shift.getShiftAccounts());
            List<Account> accountList = new ArrayList<>();

            for (ShiftAccount shiftAccount : shiftAccountList) {
                Account account = shiftAccount.getAccount();
                System.out.println(account.getFirstName());
                accountList.add(account);
            }


            for (Account account : new ArrayList<>(accountList)) {
                ResponseListShift.accountList accountData = ResponseListShift.accountList.builder()
                        .name(account.getFirstName() + " " + account.getLastName())
                        .avatar(account.getAvatar())
                        .id(account.getId())
                        .email(account.getEmail())
                        .build();
                respnoseAccountList.add(accountData);
            }

            response.setAccount(respnoseAccountList);

            responseListShiftList.add(response);
        }

        return responseListShiftList;

    }
}
