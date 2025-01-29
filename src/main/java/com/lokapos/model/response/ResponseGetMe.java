package com.lokapos.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.USER_ROLE_ENUM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseGetMe {

    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isVerifiedEmail;
    private String id;
    private ResponseBusinessDetail business;
    private String avatar;
    private USER_ROLE_ENUM role;
    private String merchantId;
    private String merchantName;
    private String merchantAddress;
    private String shiftId;
    private Boolean isActiveShift;
    private Long startShiftDate;

}
