package com.lokapos.model.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lokapos.enums.PAYMENT_METHOD_TYPE_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_STATUS_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_TYPE_ENUM;
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
public class ResponseListWalletTransaction {
    private String id;
    private WALLET_TRANSACTION_STATUS_ENUM status;
    private WALLET_TRANSACTION_TYPE_ENUM type;
    private Long amount;
    private Long createdDate;
    private PAYMENT_METHOD_TYPE_ENUM paymentMethod;
}
