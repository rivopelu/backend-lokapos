package com.lokapos.services;

import com.lokapos.entities.Account;

public interface EmailService {

    void testingSendMail();

    void SendingOtpSignUp(String otp, Account account);

}
