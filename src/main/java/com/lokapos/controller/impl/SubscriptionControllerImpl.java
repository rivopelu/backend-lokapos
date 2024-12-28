package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.SubscriptionController;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

@RequiredArgsConstructor
@BaseControllerImpl
public class SubscriptionControllerImpl implements SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Override
    public BaseResponse addSubscription(RequestSubscriptionPackage req) {
        return ResponseHelper.createBaseResponse(subscriptionService.addSubscriptionPackage(req));
    }

    @Override
    public BaseResponse getListActiveSubscriptionsPackage() {
        return ResponseHelper.createBaseResponse(subscriptionService.getListActiveSubscriptionsPackage());
    }
}
