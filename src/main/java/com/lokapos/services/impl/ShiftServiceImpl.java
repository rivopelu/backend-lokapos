package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.Shift;
import com.lokapos.entities.ShiftAccount;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestStartShift;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.ShiftAccountRepository;
import com.lokapos.repositories.ShiftRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

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

    @Override
    public String startShift(RequestStartShift req) {

        Account currentAccount = accountService.getCurrentAccount();
        List<Account> accountList = accountRepository.findAllById(req.getAccountIds());

        List<ShiftAccount> shiftAccountList = new ArrayList<>();

        if (currentAccount.getMerchant() == null) {
            throw new BadRequestException(RESPONSE_ENUM.MERCHANT_NOT_FOUND.name());
        }



        Shift shift = Shift.builder()
                .startDate(new Date().getTime())
                .isActive(true)
                .merchant(currentAccount.getMerchant())
                .build();
        EntityUtils.created(shift, currentAccount.getId());

        if(accountList.size() != req.getAccountIds().size()) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_ID_NOT_MATCH.name());
        }

        for (Account account : accountList) {
            if (account.getActiveShift() != null) {
                throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_SHIFT_ALREADY_ACTIVE.name());
            }
        }

        shiftRepository.save(shift);

        for (Account account : accountList) {
            account.setActiveShift(shift);
            ShiftAccount shiftAccount = ShiftAccount.builder()
                    .shift(shift)
                    .account(account)
                    .build();

            account.setActiveShift(shift);

            if(account.getMerchant() == null){
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
}
