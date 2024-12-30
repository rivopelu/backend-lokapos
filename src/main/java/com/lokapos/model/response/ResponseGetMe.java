package com.lokapos.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

}
