package com.lokapos.services;

import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestCreateSubscriptionOrder;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.ResponseListSubscriptionOrder;
import com.lokapos.model.response.ResponseSubscriptionPackage;
import com.lokapos.model.response.SnapPaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {
    RESPONSE_ENUM addSubscriptionPackage(RequestSubscriptionPackage req);

    List<ResponseSubscriptionPackage> getListActiveSubscriptionsPackage();

    SnapPaymentResponse orderSubscription(RequestCreateSubscriptionOrder req);

    Page<ResponseListSubscriptionOrder> getListOrderSubscriptions(Pageable pageable);
}
