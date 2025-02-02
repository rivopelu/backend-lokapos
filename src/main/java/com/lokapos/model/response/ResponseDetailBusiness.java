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
public class ResponseDetailBusiness {
    private String id;
    private String logo;
    private String name;
    private String address;
    private BigInteger provinceId;
    private BigInteger cityId;
    private BigInteger districtId;
    private BigInteger subDistrictId;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String subDistrictName;
}
