package com.lokapos.services;

import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.model.request.RequestSubscriptionPackage;

public interface SubscriptionService {
    RESPONSE_ENUM addSubscriptionPackage(RequestSubscriptionPackage req);
}
