package com.lokapos.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseCheckStatusGopayMidTrans {
    private String statusCode;
    private String transactionID;
    private String grossAmount;
    private String currency;
    private String orderID;
    private String paymentType;
    private String signatureKey;
    private String transactionStatus;
    private String fraudStatus;
    private String statusMessage;
    private String merchantID;
    private String transactionTime;
    private String settlementTime;
    private String expiryTime;
}
