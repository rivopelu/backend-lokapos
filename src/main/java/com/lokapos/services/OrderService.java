package com.lokapos.services;

import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.ResponseCheckOrderPaymentStatus;
import com.lokapos.model.response.ResponseCreateOrder;

import java.util.List;

public interface OrderService {

    ResponseCreateOrder createOrder(RequestCreateOrder req);

    ResponseCheckOrderPaymentStatus checkStatus(String orderId);
}
