package com.lokapos.model.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseCreateTransactionQris {
    public String statusCode;
    public String statusMessage;
    public String transactionId;
    public String orderId;
    public String merchantId;
    public String grossAmount;
    public String currency;
    public String paymentType;
    public String transactionTime;
    public String transactionStatus;
    public String fraudStatus;
    public List<Actions> actions;
    public String expiryTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Actions {
        public String name;
        public String method;
        public String url;
    }
}
