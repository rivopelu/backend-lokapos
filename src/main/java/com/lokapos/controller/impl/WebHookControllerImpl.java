package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.WebHookController;
import com.lokapos.model.request.ReqNotificationMidTrans;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.PaymentService;
import lombok.RequiredArgsConstructor;
import com.lokapos.utils.ResponseHelper;

@BaseControllerImpl
@RequiredArgsConstructor
public class WebHookControllerImpl implements WebHookController {

    private final PaymentService paymentService;
    @Override
    public BaseResponse postNotificationFromMidTrans(ReqNotificationMidTrans req) {
        return ResponseHelper.createBaseResponse(paymentService.postNotificationFromMidTrans(req));
    }
}
