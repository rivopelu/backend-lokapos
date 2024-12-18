package com.lokapos.services.impl;

import com.lokapos.constants.AuthConstant;
import com.lokapos.entities.Account;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.ResponseGetMe;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseGetMe getMe() throws NotAuthorizedException {
        try {
            Account account = getCurrentAccount();
            if (account == null) {
                throw new NotAuthorizedException(RESPONSE_ENUM.NOT_AUTHORIZED.name());
            }
            return ResponseGetMe.builder()
                    .fullName(account.getFirstName() + " " + account.getLastName())
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .email(account.getEmail())
                    .id(account.getId())
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
}
