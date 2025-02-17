package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.annotations.PublicAccess;
import com.lokapos.model.request.ReqNotificationMidTrans;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("webhook")
public interface WebHookController {

    @PublicAccess
    @PostMapping("v1/notification/mid-trans-payment")
    BaseResponse postNotificationFromMidTrans(@RequestBody ReqNotificationMidTrans req);
}
