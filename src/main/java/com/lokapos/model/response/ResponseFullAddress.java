package com.lokapos.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseFullAddress {
    private String fullAddress;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String subDistrictName;
    private BigInteger SubDistrict;
    private BigInteger City;
    private BigInteger province;
    private BigInteger district;
}

