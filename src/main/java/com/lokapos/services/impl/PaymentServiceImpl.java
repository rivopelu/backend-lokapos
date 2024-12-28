package com.lokapos.services.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.model.request.ReqPaymentObject;
import com.lokapos.model.response.SnapPaymentResponse;
import com.lokapos.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Override
    public SnapPaymentResponse createPayment(ReqPaymentObject req) {
        return null;
    }
}
