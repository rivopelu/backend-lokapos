package com.lokapos.model.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.ORDER_PAYMENT_METHOD_ENUM;
import com.lokapos.enums.ORDER_PLATFORM_ENUM;
import com.lokapos.enums.ORDER_TYPE_ENUM;
import com.lokapos.enums.PAYMENT_METHOD_TYPE_ENUM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestCreateOrder {
    private ORDER_PAYMENT_METHOD_ENUM paymentMethod;
    private ORDER_PLATFORM_ENUM platform;
    private ORDER_TYPE_ENUM type;
    private List<ListMenu> menuList;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ListMenu {
        private String menuId;
        private BigInteger quantity;
        private String note;
    }
}
