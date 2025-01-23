package com.lokapos.model.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseDetailOrder {
    private String id;
    private ORDER_STATUS_ENUM status;
    private ORDER_PAYMENT_STATUS_ENUM paymentStatus;
    private BigInteger totalOrder;
    private BigInteger totalItem;
    private BigInteger code;
    private ORDER_PLATFORM_ENUM platform;
    private ORDER_TYPE_ENUM type;
    private ORDER_PAYMENT_METHOD_ENUM paymentMethod;
    private List<MenuList> menuList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MenuList {
        private String name;
        private String id;
        private String image;
        private BigInteger quantity;
    }

}
