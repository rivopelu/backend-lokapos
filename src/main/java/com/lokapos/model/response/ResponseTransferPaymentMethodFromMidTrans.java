package com.lokapos.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseTransferPaymentMethodFromMidTrans {
    public String status_code;
    public String status_message;
    public String transaction_id;
    public String order_id;
    public String merchant_id;
    public String gross_amount;
    public String currency;
    public String payment_type;
    public String transaction_time;
    public String transaction_status;
    public ArrayList<VaNumber> va_numbers;
    public String fraud_status;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class VaNumber{
        public String bank;
        public String va_number;
    }
}
