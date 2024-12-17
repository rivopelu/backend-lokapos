package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.USER_ROLE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestSignIn;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.ResponseSignIn;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import javax.swing.text.html.parser.Entity;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String ping() {
        return "PONG";
    }

    @Override
    public String signUp(RequestSignUp req) {
        boolean checkEmail = accountRepository.existsByEmailAndActiveIsTrue(req.getEmail());
        if (checkEmail) {
            throw new BadRequestException(RESPONSE_ENUM.EMAIL_ALREADY_EXIST.name());
        }


        try {
            String encodedPassword = passwordEncoder.encode(req.getPassword());
            Account account = Account.builder()
                    .firstName(req.getFirstName())
                    .lastName(req.getLastName())
                    .email(req.getEmail())
                    .role(USER_ROLE_ENUM.ADMIN)
                    .password(encodedPassword)
                    .build();
            EntityUtils.created(account, "SYSTEM");
            accountRepository.save(account);
            return "SUCCESS";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseSignIn signIn(RequestSignIn req) {
        try {
            return  ResponseSignIn.builder().accessToken("TOKEN").build();
        }catch (Exception e){
            throw new SystemErrorException(e);
        }
    }
}
