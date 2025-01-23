package com.lokapos.model.request;


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
public class ReqPaymentObject {
    private TransactionDetail transactionDetail;
    private ItemsDetail itemsDetail;
    private CustomersDetails customersDetails;
    private BankTransfer bankTransfer;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TransactionDetail {
        private String orderId;
        private BigInteger grossAmount;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ItemsDetail {
        private String id;
        private BigInteger price;
        private BigInteger quantity;
        private String name;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CustomersDetails {
        private String firstName;
        private String email;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonSerialize
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class BankTransfer {
        private String bankName;
    }

}
