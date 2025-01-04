package utils;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.entities.SubscriptionOrder;
import com.lokapos.entities.SubscriptionPackage;
import com.lokapos.enums.PAYMENT_METHOD_TYPE_ENUM;
import com.lokapos.model.request.ReqPaymentObject;

import java.math.BigInteger;

public class PaymentRequestUtils {

    public static ReqPaymentObject parseRequestPayment(SubscriptionOrder order, Account account, SubscriptionPackage subscriptionPackage, Business business) {
        switch (order.getPaymentMethod()) {
            case BANK_TRANSFER_BCA -> {
                return parseBankTransferString(order, account, subscriptionPackage, business, "bca");
            }
            case BANK_TRANSFER_BRI -> {
                return parseBankTransferString(order, account, subscriptionPackage, business, "bri");
            }
            case BANK_TRANSFER_BNI -> {
                return parseBankTransferString(order, account, subscriptionPackage, business, "bni");
            }
            default -> {
                return null;
            }
        }

    }

    private static ReqPaymentObject parseBankTransferString(SubscriptionOrder order, Account account, SubscriptionPackage subscriptionPackage, Business business, String bank) {
        ReqPaymentObject generateBuilder = generateTransactionDetail(order, account, subscriptionPackage, business);
        ReqPaymentObject.BankTransfer bankTransfer = ReqPaymentObject.BankTransfer.builder()
                .bankName(bank)
                .build();

        generateBuilder.setBankTransfer(bankTransfer);
        return generateBuilder;
    }

    private static ReqPaymentObject generateTransactionDetail(SubscriptionOrder order, Account account, SubscriptionPackage subscriptionPackage, Business business) {
        ReqPaymentObject.TransactionDetail transactionDetail = ReqPaymentObject.TransactionDetail.builder()
                .orderId(order.getId())
                .grossAmount(subscriptionPackage.getPrice())
                .build();
        ReqPaymentObject.ItemsDetail itemsDetail = ReqPaymentObject.ItemsDetail.builder()
                .id(subscriptionPackage.getId())
                .name(subscriptionPackage.getName())
                .quantity(BigInteger.ONE)
                .price(subscriptionPackage.getPrice())
                .build();
        ReqPaymentObject.CustomersDetails customersDetails = ReqPaymentObject.CustomersDetails.builder()
                .firstName(business.getBusinessName())
                .email(account.getId())
                .build();
        return ReqPaymentObject.builder()
                .transactionDetail(transactionDetail)
                .itemsDetail(itemsDetail)
                .customersDetails(customersDetails)
                .build();
    }
}
