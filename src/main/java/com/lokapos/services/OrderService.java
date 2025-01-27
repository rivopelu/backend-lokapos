package com.lokapos.services;

import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.ResponseCheckOrderPaymentStatus;
import com.lokapos.model.response.ResponseCreateOrder;
import com.lokapos.model.response.ResponseDetailOrder;
import com.lokapos.model.response.ResponseListOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    ResponseCreateOrder createOrder(RequestCreateOrder req);

    ResponseCheckOrderPaymentStatus checkStatus(String orderId);

    Page<ResponseListOrder> getListOrder(Pageable pageable);

    ResponseDetailOrder getOrderDetail(String id);

    String readyOrder(String id);
}
