package com.lokapos.services.impl;

import com.lokapos.entities.MenuOrder;
import com.lokapos.entities.ServingOrder;
import com.lokapos.entities.ServingMenu;
import com.lokapos.enums.ORDER_PAYMENT_METHOD_ENUM;
import com.lokapos.enums.ORDER_PAYMENT_STATUS_ENUM;
import com.lokapos.enums.ORDER_STATUS_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.ResponseCreateOrder;
import com.lokapos.repositories.MenuOrderRepository;
import com.lokapos.repositories.ServingOrderRepository;
import com.lokapos.repositories.ServingMenuRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.OrderService;
import com.lokapos.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.EntityUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ServingOrderRepository servingOrderRepository;
    private final AccountService accountService;
    private final ServingMenuRepository servingMenuRepository;
    private final MenuOrderRepository menuOrderRepository;
    private final PaymentService paymentService;

    @Override
    public ResponseCreateOrder createOrder(RequestCreateOrder req) {
        try {
            ServingOrder servingOrderBuilder = ServingOrder.builder()
                    .status(ORDER_STATUS_ENUM.PENDING)
                    .paymentStatus(ORDER_PAYMENT_STATUS_ENUM.PENDING)
                    .paymentMethod(req.getPaymentMethod())
                    .build();

            ServingOrder servingOrder = buildMenuOrders(req.getMenuList(), servingOrderBuilder);
            String qrisUrl = null;
            if (req.getPaymentMethod().equals(ORDER_PAYMENT_METHOD_ENUM.QRIS)) {
                qrisUrl = paymentService.createPaymentUsingEWallet(servingOrder);
            }
            return ResponseCreateOrder.builder()
                    .qrisUrl(qrisUrl)
                    .orderId(servingOrder.getId())
                    .paymentMethod(req.getPaymentMethod())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<Object> checkStatus(String orderId) {
        return List.of();
    }

    private ServingOrder buildMenuOrders(List<RequestCreateOrder.ListMenu> listMenus, ServingOrder servingOrder) {
        int index = 0;
        BigInteger totalTransaction = BigInteger.ZERO;
        try {
            List<MenuOrder> menuOrders = new ArrayList<>();
            List<ServingMenu> servingMenuList = servingMenuRepository.findAllById(listMenus.stream().map(RequestCreateOrder.ListMenu::getMenuId).toList());

            System.out.println(servingMenuList);
            for (RequestCreateOrder.ListMenu listMenu : listMenus) {
                ServingMenu servingMenu = servingMenuList.get(index);
                BigInteger totalPrice = servingMenu.getPrice().multiply(listMenu.getQuantity());
                MenuOrder menuOrder = MenuOrder.builder()
                        .menu(servingMenu)
                        .servingOrder(servingOrder)
                        .quantity(listMenu.getQuantity())
                        .note(listMenu.getNote())
                        .pricePerQty(servingMenu.getPrice())
                        .totalPrice(totalPrice)
                        .build();
                index = index + 1;
                BigInteger calclateTotal = totalTransaction.add(totalPrice);
                totalTransaction = calclateTotal;
                menuOrders.add(menuOrder);
                EntityUtils.created(menuOrder, accountService.getCurrentAccountId());

            }


            EntityUtils.created(servingOrder, accountService.getCurrentAccountId());
            servingOrder.setTotalTransaction(totalTransaction);
            servingOrder = servingOrderRepository.save(servingOrder);
            menuOrderRepository.saveAll(menuOrders);
            return servingOrder;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
