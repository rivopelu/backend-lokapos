package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@BaseController("order")
public interface OrderController {

    @PostMapping("v1/create")
    BaseResponse createOrder(@RequestBody List<RequestCreateOrder> req);

}
