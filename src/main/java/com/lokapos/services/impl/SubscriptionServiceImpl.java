package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.Business;
import com.lokapos.entities.SubscriptionOrder;
import com.lokapos.entities.SubscriptionPackage;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.enums.SUBSCRIPTION_ORDER_STATUS_ENUM;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.ReqPaymentObject;
import com.lokapos.model.request.RequestCreateSubscriptionOrder;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.ResponseSubscriptionPackage;
import com.lokapos.model.response.SnapPaymentResponse;
import com.lokapos.repositories.SubscriptionOrderRepository;
import com.lokapos.repositories.SubscriptionPackageRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.PaymentService;
import com.lokapos.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AccountService accountService;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final PaymentService paymentService;
    private final SubscriptionOrderRepository subscriptionOrderRepository;

    @Override
    public RESPONSE_ENUM addSubscriptionPackage(RequestSubscriptionPackage req) {
        try {

            SubscriptionPackage subscriptionPackage = SubscriptionPackage.builder()
                    .name(req.getName())
                    .description(req.getDescription())
                    .durationPerDay(req.getDurationPerDay())
                    .price(req.getPrice())
                    .build();
            EntityUtils.created(subscriptionPackage, accountService.getCurrentAccountId());
            subscriptionPackageRepository.save(subscriptionPackage);
            return RESPONSE_ENUM.SUCCESS;

        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseSubscriptionPackage> getListActiveSubscriptionsPackage() {

        List<SubscriptionPackage> subscriptionPackageList = subscriptionPackageRepository.findAllByActiveIsTrue();

        try {
            List<ResponseSubscriptionPackage> responseSubscriptionPackageList = new ArrayList<>();
            for (SubscriptionPackage subscriptionPackage : subscriptionPackageList) {
                ResponseSubscriptionPackage responseSubscriptionPackage = ResponseSubscriptionPackage.builder()
                        .name(subscriptionPackage.getName())
                        .description(subscriptionPackage.getDescription())
                        .active(subscriptionPackage.getActive())
                        .duration(subscriptionPackage.getDurationPerDay())
                        .price(subscriptionPackage.getPrice())
                        .build();
                responseSubscriptionPackageList.add(responseSubscriptionPackage);
            }
            return responseSubscriptionPackageList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public SnapPaymentResponse orderSubscription(RequestCreateSubscriptionOrder req) {

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findById(req.getPackageId()).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.PACKAGE_NOT_FOUND.name()));
        Account account = accountService.getCurrentAccount();
        if(account.getBusiness() == null){
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
        Business business = account.getBusiness();


        ReqPaymentObject.TransactionDetail transactionDetail = ReqPaymentObject.TransactionDetail.builder()
                .orderId(subscriptionPackage.getId())
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
        ReqPaymentObject reqPaymentObject = ReqPaymentObject.builder()
                .transactionDetail(transactionDetail)
                .itemsDetail(itemsDetail)
                .customersDetails(customersDetails)
                .build();

        SnapPaymentResponse snapPaymentResponse = paymentService.createPayment(reqPaymentObject);
        SubscriptionOrder subscriptionOrder = SubscriptionOrder.builder()
                .subscriptionPackage(subscriptionPackage)
                .business(business)
                .totalTransaction(subscriptionPackage.getPrice())
                .status(SUBSCRIPTION_ORDER_STATUS_ENUM.PENDING)
                .build();
        EntityUtils.created(subscriptionOrder, account.getId());
        subscriptionOrderRepository.save(subscriptionOrder);
        try {
            return snapPaymentResponse;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
