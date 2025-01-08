package com.lokapos.services.impl;

import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String createOrder(List<RequestCreateOrder> req) {
        return "HELLO";
    }
}
