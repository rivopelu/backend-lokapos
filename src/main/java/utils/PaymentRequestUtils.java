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
        if (order.getPaymentMethod().equals(PAYMENT_METHOD_TYPE_ENUM.BANK_TRANSFER_BCA)) {
            return parseBankTransferBca(order, account, subscriptionPackage, business);
        }
        return null;
    }

    private static ReqPaymentObject parseBankTransferBca(SubscriptionOrder order, Account account, SubscriptionPackage subscriptionPackage, Business business) {
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

        ReqPaymentObject.BankTransfer bankTransfer = ReqPaymentObject.BankTransfer.builder()
                .bankName("bca")
                .build();
        ReqPaymentObject reqPaymentObject = ReqPaymentObject.builder()
                .transactionDetail(transactionDetail)
                .itemsDetail(itemsDetail)
                .customersDetails(customersDetails)
                .bankTransfer(bankTransfer)
                .build();

        return reqPaymentObject;
    }
}
