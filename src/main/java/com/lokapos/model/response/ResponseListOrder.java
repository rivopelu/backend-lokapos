package com.lokapos.model.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.ORDER_PAYMENT_METHOD_ENUM;
import com.lokapos.enums.ORDER_STATUS_ENUM;
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
public class ResponseListOrder {
    private String id;
    private ORDER_STATUS_ENUM status;
    private BigInteger totalOrder;
    private BigInteger totalItem;
    private ORDER_PAYMENT_METHOD_ENUM paymentMethod;
}
