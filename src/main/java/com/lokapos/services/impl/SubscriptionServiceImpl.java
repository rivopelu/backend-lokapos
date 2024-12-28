package com.lokapos.services.impl;

import com.lokapos.entities.SubscriptionPackage;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.repositories.SubscriptionPackageRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

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
}
