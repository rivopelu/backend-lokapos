package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("order")
public interface OrderController {

    @PostMapping("v1/create")
    BaseResponse createOrder(@RequestBody RequestCreateOrder req);

    @GetMapping("v1/check-status/{orderId}")
    BaseResponse checkStatus(@PathVariable String orderId);

    @GetMapping("v1/list")
    BaseResponse getListOrder(Pageable pageable);

    @GetMapping("v1/detail/{id}")
    BaseResponse getOrderDetail(@PathVariable String id);

    @PatchMapping("v1/ready/{id}")
    BaseResponse readyOrder(@PathVariable("id") String id);
}
