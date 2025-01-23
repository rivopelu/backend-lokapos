package com.lokapos.model.request;

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
public class ReqNotificationMidTrans {
    private String transactionTime;
    private String transactionStatus;
    private String transactionId;
    private String statusMessage;
    private Integer statusCode;
    private String signatureKey;
    private String paymentType;
    private String orderId;
    private String merchantId;
    private String maskedCard;
    private Double grossAmount;
    private String fraudStatus;
    private String eci;
    private String currency;
    private String channelResponseMessage;
    private String channelResponseCode;
    private String cardType;
    private String bank;
    private String approvalCode;
}

