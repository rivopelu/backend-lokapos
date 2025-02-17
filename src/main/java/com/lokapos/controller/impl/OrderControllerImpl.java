package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.OrderController;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.OrderService;
import org.springframework.data.domain.Pageable;
import com.lokapos.utils.ResponseHelper;

@BaseControllerImpl
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public BaseResponse createOrder(RequestCreateOrder req) {
        return ResponseHelper.createBaseResponse(orderService.createOrder(req));
    }

    @Override
    public BaseResponse checkStatus(String orderId) {
        return ResponseHelper.createBaseResponse(orderService.checkStatus(orderId));
    }

    @Override
    public BaseResponse getListOrder(Pageable pageable) {

        return ResponseHelper.createBaseResponse(orderService.getListOrder(pageable));
    }

    @Override
    public BaseResponse getOrderDetail(String id) {
        return ResponseHelper.createBaseResponse(orderService.getOrderDetail(id));
    }

    @Override
    public BaseResponse readyOrder(String id) {
        return ResponseHelper.createBaseResponse(orderService.readyOrder(id));
    }



}
