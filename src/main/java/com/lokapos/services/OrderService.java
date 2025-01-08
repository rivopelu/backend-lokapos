package com.lokapos.services;

import com.lokapos.model.request.RequestCreateOrder;

import java.util.List;

public interface OrderService {

    String createOrder(RequestCreateOrder req);

}
