package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.OtpAndToken;
import com.lokapos.enums.OTP_AND_TOKEN_TYPE_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.USER_ROLE_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestSignIn;
import com.lokapos.model.request.RequestSignUp;
import com.lokapos.model.response.ResponseSignIn;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.repositories.OtpAndTokenRepository;
import com.lokapos.services.AuthService;
import com.lokapos.services.EmailService;
import com.lokapos.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utils.EntityUtils;
import utils.UtilsHelper;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final OtpAndTokenRepository otpAndTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtService jwtService;

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

            String otp = UtilsHelper.generateNumericOTP();
            String encodedPassword = passwordEncoder.encode(req.getPassword());
            Account account = Account.builder()
                    .firstName(req.getFirstName())
                    .lastName(req.getLastName())
                    .email(req.getEmail())
                    .isVerifiedEmail(false)
                    .role(USER_ROLE_ENUM.ADMIN)
                    .password(encodedPassword)
                    .build();
            EntityUtils.created(account, "SYSTEM");
            account = accountRepository.save(account);
            OtpAndToken otpAndToken = OtpAndToken.builder()
                    .otp(otp)
                    .type(OTP_AND_TOKEN_TYPE_ENUM.SIGN_UP_OTP)
                    .account(account)
                    .build();
            EntityUtils.created(otpAndToken, account.getId());
            otpAndTokenRepository.save(otpAndToken);
            emailService.SendingOtpSignUp(otp, account);
            return "SUCCESS";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseSignIn signIn(RequestSignIn req) {
        Account account = accountRepository.findByEmailAndActiveIsTrue(req.getEmail()).orElseThrow(() -> new BadRequestException(RESPONSE_ENUM.SIGN_IN_FAILED.name()));
        try {
            return getSignIn(account, req.getPassword());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private ResponseSignIn getSignIn(Account account, String password) {
        try {
            Authentication authentication;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            return ResponseSignIn.builder().accessToken(jwt).build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
