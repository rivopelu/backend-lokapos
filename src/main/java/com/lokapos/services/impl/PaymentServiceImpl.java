package com.lokapos.services.impl;

import com.lokapos.entities.Business;
import com.lokapos.entities.SubscriptionOrder;
import com.lokapos.entities.TransactionNotificationSubscription;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.SUBSCRIPTION_ORDER_STATUS_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.ReqNotificationMidTrans;
import com.lokapos.model.request.ReqPaymentObject;
import com.lokapos.model.response.ResponseTransferPaymentMethodFromMidTrans;
import com.lokapos.model.response.SnapPaymentResponse;
import com.lokapos.repositories.BusinessRepository;
import com.lokapos.repositories.SubscriptionOrderRepository;
import com.lokapos.repositories.TransactionNotificationSubscriptionRepository;
import com.lokapos.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.EntityUtils;

import java.math.BigInteger;
import java.util.*;

import static utils.UrlString.CHARGE_API_PAYMENT;
import static utils.UrlString.GET_PAYMENT_SNAP_MID_TRANS_URL;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final BusinessRepository businessRepository;
    private final TransactionNotificationSubscriptionRepository transactionNotificationSubscriptionRepository;

    @Value("${mt.server-key}")
    private String mtServerKey;

    @Value("${mt.api-url}")
    private String mtUrl;

    @Value("${mt.mt-api-url}")
    private String mtApiUrl;

    @Override
    public SnapPaymentResponse createPayment(ReqPaymentObject req) {
        String url = mtUrl + GET_PAYMENT_SNAP_MID_TRANS_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String authString = mtServerKey + ":";
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
        headers.set("Authorization", "Basic " + encodedAuthString);

        Map<String, Object> body = new HashMap<>();

        body.put("transaction_details", generateTransactionDetail(req.getTransactionDetail()));
        body.put("item_detail", generateItemsDetail(req.getItemsDetail()));
        body.put("payment_type", generateItemsDetail(req.getItemsDetail()));
//        body.put("customer_details", generateCustomersDetail(req.getCustomersDetails()));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SnapPaymentResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SnapPaymentResponse.class);

        try {
            return response.getBody();

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String postNotificationFromMidTrans(ReqNotificationMidTrans req) {
        Optional<SubscriptionOrder> findOrder = subscriptionOrderRepository.findById(req.getOrderId());
        if (findOrder.isEmpty()) {
            throw new BadRequestException("Order not found");
        }
        SubscriptionOrder subscriptionOrder = null;

        subscriptionOrder = findOrder.get();

        if ("settlement".equals(req.getTransactionStatus())) {

            Business business = subscriptionOrder.getBusiness();
            Long newExpiredDate = getALong(subscriptionOrder, business);
            business.setSubscriptionExpireDate(newExpiredDate);

            subscriptionOrder.setStatus(SUBSCRIPTION_ORDER_STATUS_ENUM.SUCCESS);
            business.setIsActiveSubscription(true);
            subscriptionOrderRepository.save(subscriptionOrder);
            businessRepository.save(business);
        }

        TransactionNotificationSubscription detail = TransactionNotificationSubscription.builder()
                .transactionTime(new Date().getTime())
                .transactionStatus(req.getTransactionStatus())
                .transactionId(req.getTransactionId())
                .statusMessage(req.getStatusMessage())
                .statusCode(req.getStatusCode())
                .signatureKey(req.getSignatureKey())
                .paymentType(req.getPaymentType())
                .orderId(req.getOrderId())
                .merchantId(req.getMerchantId())
                .maskedCard(req.getMaskedCard())
                .grossAmount(req.getGrossAmount())
                .fraudStatus(req.getFraudStatus())
                .eci(req.getEci())
                .currency(req.getCurrency())
                .channelResponseMessage(req.getChannelResponseMessage())
                .channelResponseCode(req.getChannelResponseCode())
                .cardType(req.getCardType())
                .bank(req.getBank())
                .approvalCode(req.getApprovalCode())
                .subscriptionOrder(subscriptionOrder)
                .build();


        EntityUtils.created(detail, "MID_TRANS");

        try {
            transactionNotificationSubscriptionRepository.save(detail);
            return RESPONSE_ENUM.SUCCESS.name();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String createPaymentCustomInterface(ReqPaymentObject req) {
        String url = mtApiUrl + CHARGE_API_PAYMENT;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String authString = mtServerKey + ":";
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
        headers.set("Authorization", "Basic " + encodedAuthString);

        Map<String, Object> body = new HashMap<>();

        body.put("transaction_details", generateTransactionDetail(req.getTransactionDetail()));
        body.put("item_detail", generateItemsDetail(req.getItemsDetail()));
        body.put("bank_transfer", generateBankTransfer(req.getBankTransfer()));
        body.put("payment_type", "bank_transfer");
//        body.put("customer_details", generateCustomersDetail(req.getCustomersDetails()));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseTransferPaymentMethodFromMidTrans> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTransferPaymentMethodFromMidTrans.class);
        String vaNumber = Objects.requireNonNull(response.getBody()).getVa_numbers().getFirst().va_number;
        SubscriptionOrder subscriptionOrder = subscriptionOrderRepository.findById(req.getTransactionDetail().getOrderId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
        subscriptionOrder.setPaymentCode(vaNumber);
        subscriptionOrderRepository.save(subscriptionOrder);
        return subscriptionOrder.getId();
    }

    private static Long getALong(SubscriptionOrder subscriptionOrder, Business business) {
        BigInteger subscriptionDurationPerDay = subscriptionOrder.getSubscriptionPackage().getDurationPerDay();
        long currentExpiredDate = new Date().getTime();
        if (business.getSubscriptionExpireDate() != null) {
            currentExpiredDate = business.getSubscriptionExpireDate();
        }

        BigInteger millisecondsInADay = BigInteger.valueOf(86400000);
        BigInteger subscriptionDuration = millisecondsInADay.multiply(subscriptionDurationPerDay);
        return currentExpiredDate + subscriptionDuration.longValue();
    }


    private Map<String, Object> generateTransactionDetail(ReqPaymentObject.TransactionDetail detail) {
        Map<String, Object> data = new HashMap<>();
        data.put("order_id", detail.getOrderId());
        data.put("gross_amount", detail.getGrossAmount());
        return data;
    }


    private Map<String, Object> generateBankTransfer(ReqPaymentObject.BankTransfer detail) {
        Map<String, Object> data = new HashMap<>();
        data.put("bank", detail.getBankName());
        return data;
    }


    private List<Map<String, Object>> generateItemsDetail(ReqPaymentObject.ItemsDetail detail) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", detail.getId());
        data.put("price", detail.getPrice());
        data.put("name", detail.getName());

        List<Map<String, Object>> result = new ArrayList<>();
        result.add(data);

        return result;
    }


    private Map<String, Object> generateCustomersDetail(ReqPaymentObject.CustomersDetails detail) {
        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", detail.getFirstName());
        customerDetails.put("email", detail.getEmail());
        return customerDetails;
    }
}
