package com.lokapos.repositories;

import com.lokapos.entities.Account;
import com.lokapos.entities.OtpAndToken;
import com.lokapos.enums.OTP_AND_TOKEN_TYPE_ENUM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpAndTokenRepository extends JpaRepository<OtpAndToken, String> {

    Optional<OtpAndToken> findOneByOtpAndAccountIdAndActiveIsTrue(String otp, String accountId);

    @Query(value = "select o from OtpAndToken as o where o.otp = :otp and o.account.id = :accountId")
    Optional<OtpAndToken> queryFindOtp(String otp, String accountId);

    Optional<OtpAndToken> findByAccountIdAndActiveIsTrueAndType(String id, OTP_AND_TOKEN_TYPE_ENUM otpAndTokenTypeEnum);
}
