package com.lokapos.model.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.PAYMENT_METHOD_TYPE_ENUM;
import com.lokapos.enums.SUBSCRIPTION_ORDER_STATUS_ENUM;
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
public class ResponseConfirmationPayment {
    private PAYMENT_METHOD_TYPE_ENUM paymentMethod;
    private String paymentCode;
    private String orderId;
    private BigInteger totalTransaction;
    private SUBSCRIPTION_ORDER_STATUS_ENUM status;
    private Long paymentSubscriptionExpire;

}
