package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.OrderController;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.OrderService;
import lombok.RequiredArgsConstructor;
import utils.ResponseHelper;

import java.util.List;

@BaseControllerImpl
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public BaseResponse createOrder(RequestCreateOrder req) {
        return ResponseHelper.createBaseResponse(orderService.createOrder(req));
    }

}
