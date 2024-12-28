package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.annotations.SuperAdminAccess;
import com.lokapos.model.request.RequestSubscriptionPackage;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("subscription")
public interface SubscriptionController {

    @SuperAdminAccess
    @PostMapping("v1/add")
    BaseResponse addSubscription(@RequestBody RequestSubscriptionPackage req);

    @GetMapping("v1/list/active")
    BaseResponse getListActiveSubscriptionsPackage();

}
