package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.SubscriptionController;
import com.lokapos.model.request.RequestCreateSubscriptionOrder;
import com.lokapos.model.request.RequestCreateSubscriptionV2;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @Override
    public BaseResponse orderSubscription(RequestCreateSubscriptionOrder req) {

        return ResponseHelper.createBaseResponse(subscriptionService.orderSubscription(req));
    }


    @Override
    public BaseResponse getListOrderSubscriptions(Pageable pageable) {
        return ResponseHelper.createBaseResponse(subscriptionService.getListOrderSubscriptions(pageable));
    }

    @Override
    public BaseResponse orderSubscriptionV2(RequestCreateSubscriptionV2 req) {
        return ResponseHelper.createBaseResponse(subscriptionService.orderSubscriptionV2(req));
    }

    @Override
    public BaseResponse orderSubscriptionConfirmationPayment(String id) {
        return ResponseHelper.createBaseResponse(subscriptionService.orderSubscriptionConfirmationPayment(id));
    }
}
