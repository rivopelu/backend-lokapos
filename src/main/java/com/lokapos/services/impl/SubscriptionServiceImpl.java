package com.lokapos.services.impl;

import com.lokapos.entities.SubscriptionPackage;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateSubscriptionOrder;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.ResponseSubscriptionPackage;
import com.lokapos.repositories.SubscriptionPackageRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AccountService accountService;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

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
    public RESPONSE_ENUM orderSubscription(RequestCreateSubscriptionOrder req) {
        try {
            return RESPONSE_ENUM.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
