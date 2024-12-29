package com.lokapos.services;

import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateSubscriptionOrder;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.ResponseSubscriptionPackage;
import com.lokapos.model.response.SnapPaymentResponse;

import java.util.List;

public interface SubscriptionService {
    RESPONSE_ENUM addSubscriptionPackage(RequestSubscriptionPackage req);

    List<ResponseSubscriptionPackage> getListActiveSubscriptionsPackage();

    SnapPaymentResponse orderSubscription(RequestCreateSubscriptionOrder req);
}
