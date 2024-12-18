package com.lokapos.entities;


import com.lokapos.enums.OTP_AND_TOKEN_TYPE_ENUM;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_and_token")
public class OtpAndToken extends BaseEntity {

    @Column(name = "otp")
    private String otp;

    @Column(name = "token")
    private String token;

    @Column(name = "otp_and_token_type")
    private OTP_AND_TOKEN_TYPE_ENUM type;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;


}
