package com.lokapos.services.impl;

import com.lokapos.constants.FlagConstants;
import com.lokapos.entities.*;
import com.lokapos.enums.ORDER_PAYMENT_STATUS_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.SUBSCRIPTION_ORDER_STATUS_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_STATUS_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.ReqNotificationMidTrans;
import com.lokapos.model.request.ReqPaymentObject;
import com.lokapos.model.response.*;
import com.lokapos.repositories.*;
import com.lokapos.services.AccountService;
import com.lokapos.services.PaymentService;
import com.lokapos.utils.UtilsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lokapos.utils.EntityUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lokapos.utils.UrlString.CHARGE_API_PAYMENT;
import static com.lokapos.utils.UrlString.GET_PAYMENT_SNAP_MID_TRANS_URL;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final BusinessRepository businessRepository;
    private final TransactionNotificationSubscriptionRepository transactionNotificationSubscriptionRepository;
    private final AccountService accountService;
    private final PaymentActionRepository paymentActionRepository;
    private final ServingOrderRepository servingOrderRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletRepository walletRepository;

    @Value("${mt.server-key}")
    private String mtServerKey;

    @Value("${mt.api-url}")
    private String mtUrl;

    @Value("${mt.mt-api-url}")
    private String mtApiUrl;


    @Override
    public SnapPaymentResponse createPayment(ReqPaymentObject req) {
        String url = mtUrl + GET_PAYMENT_SNAP_MID_TRANS_URL;
        HttpHeaders headers = generateHeaderMidTrans();
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

        ResponseParseFlagId parseFlagId = UtilsHelper.parseIdFromFlg(req.getOrderId());
        if (parseFlagId.getFlag() != null) {
            if (Objects.equals(parseFlagId.getFlag(), FlagConstants.topUp)) {
                return receiveNotificationTopup(parseFlagId.getId(), req);
            }

        }

        Optional<SubscriptionOrder> findOrder = subscriptionOrderRepository.findById(req.getOrderId());
        if (findOrder.isEmpty()) {
            throw new BadRequestException("Order not found");
        }


        SubscriptionOrder subscriptionOrder;

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

        HttpHeaders headers = generateHeaderMidTrans();
        Map<String, Object> body = new HashMap<>();

        body.put("transaction_details", generateTransactionDetail(req.getTransactionDetail()));
        body.put("item_detail", generateItemsDetail(req.getItemsDetail()));
        body.put("bank_transfer", generateBankTransfer(req.getBankTransfer()));
        body.put("payment_type", "bank_transfer");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseTransferPaymentMethodFromMidTrans> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTransferPaymentMethodFromMidTrans.class);
        String vaNumber = Objects.requireNonNull(response.getBody()).getVa_numbers().getFirst().va_number;
        SubscriptionOrder subscriptionOrder = subscriptionOrderRepository.findById(req.getTransactionDetail().getOrderId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
        subscriptionOrder.setPaymentCode(vaNumber);
        subscriptionOrderRepository.save(subscriptionOrder);
        return subscriptionOrder.getId();
    }

    public String createPaymentTopup(ReqPaymentObject req) {
        String url = mtApiUrl + CHARGE_API_PAYMENT;

        HttpHeaders headers = generateHeaderMidTrans();
        Map<String, Object> body = new HashMap<>();

        body.put("transaction_details", generateTransactionDetail(req.getTransactionDetail()));
        if (req.getItemsDetail() != null) {
            body.put("item_detail", generateItemsDetail(req.getItemsDetail()));

        }
        if (req.getBankTransfer() != null) {
            body.put("bank_transfer", generateBankTransfer(req.getBankTransfer()));
            body.put("payment_type", "bank_transfer");
        }

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseTransferPaymentMethodFromMidTrans> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseTransferPaymentMethodFromMidTrans.class);

        return Objects.requireNonNull(response.getBody()).getVa_numbers().getFirst().va_number;
    }

    @Override
    public String createPaymentUsingEWallet(ServingOrder order) {
        try {
            String url = mtApiUrl + CHARGE_API_PAYMENT;
            String currentAccountId = accountService.getCurrentAccountId();

            Map<String, Object> body = new HashMap<>();

            ReqPaymentObject.TransactionDetail transactionDetail = ReqPaymentObject.TransactionDetail.builder()
                    .grossAmount(order.getTotalTransaction())
                    .orderId(order.getId())
                    .build();

            body.put("transaction_details", generateTransactionDetail(transactionDetail));
            body.put("payment_type", "gopay");
            HttpHeaders headers = generateHeaderMidTrans();
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ResponseCreateTransactionQris> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseCreateTransactionQris.class);
            List<ResponseCreateTransactionQris.Actions> listAction = Objects.requireNonNull(response.getBody()).getActions();
            List<PaymentAction> paymentActions = new ArrayList<>();
            for (ResponseCreateTransactionQris.Actions action : listAction) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(response.getBody().getTransactionTime());

                PaymentAction paymentAction = PaymentAction.builder()
                        .servingOrder(order)
                        .name(action.getName())
                        .method(action.getMethod())
                        .url(action.getUrl())
                        .expireTime(date.getTime())
                        .build();
                EntityUtils.created(paymentAction, currentAccountId);

                paymentActions.add(paymentAction);
            }
            paymentActionRepository.saveAll(paymentActions);
            PaymentAction paymentAction = paymentActions.getFirst();
            return paymentAction.getUrl();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ORDER_PAYMENT_STATUS_ENUM checkStatusOrder(String orderId) {
        try {
            HttpHeaders headers = generateHeaderMidTrans();
            ServingOrder servingOrder = servingOrderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
            PaymentAction paymentAction = paymentActionRepository.findByServingOrderIdAndName(orderId, "get-status").orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(null, headers);
            ORDER_PAYMENT_STATUS_ENUM status = ORDER_PAYMENT_STATUS_ENUM.PENDING;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ResponseCheckStatusGopayMidTrans> response = restTemplate.exchange(paymentAction.getUrl(), HttpMethod.GET, requestEntity, ResponseCheckStatusGopayMidTrans.class);
            if (response.getBody() != null) {
                if (response.getBody().getTransactionStatus().equals("settlement")) {
                    servingOrder.setPaymentStatus(ORDER_PAYMENT_STATUS_ENUM.SUCCESS);
                    status = ORDER_PAYMENT_STATUS_ENUM.SUCCESS;
                    servingOrderRepository.save(servingOrder);
                }
            }
            return status;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
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


    private HttpHeaders generateHeaderMidTrans() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String authString = mtServerKey + ":";
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
        headers.set("Authorization", "Basic " + encodedAuthString);
        return headers;
    }

    private String receiveNotificationTopup(String orderId, ReqNotificationMidTrans req) {
        WalletTransaction walletTransaction = walletTransactionRepository.findById(req.getOrderId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
        walletTransaction.setStatus(WALLET_TRANSACTION_STATUS_ENUM.SUCCESS);
        Wallet wallet = walletTransaction.getWallet();
        wallet.setBalance(wallet.getBalance() + walletTransaction.getAmount());
        walletRepository.save(wallet);
        return orderId;
    }
}
