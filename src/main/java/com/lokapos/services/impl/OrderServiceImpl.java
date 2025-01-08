package com.lokapos.services.impl;

import com.lokapos.entities.MenuOrder;
import com.lokapos.entities.ServingOrder;
import com.lokapos.entities.ServingMenu;
import com.lokapos.enums.ORDER_STATUS_ENUM;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.repositories.MenuOrderRepository;
import com.lokapos.repositories.ServingOrderRepository;
import com.lokapos.repositories.ServingMenuRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.OrderService;
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

    @Override
    public String createOrder(RequestCreateOrder req) {
        try {
            ServingOrder servingOrderBuilder = ServingOrder.builder()
                    .status(ORDER_STATUS_ENUM.PENDING)
                    .paymentMethod(req.getPaymentMethod())
                    .build();

            List<MenuOrder> menuOrderList = buildMenuOrders(req.getMenuList(), servingOrderBuilder);
            return RESPONSE_ENUM.SUCCESS.name();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private List<MenuOrder> buildMenuOrders(List<RequestCreateOrder.ListMenu> listMenus, ServingOrder servingOrder) {
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
            servingOrderRepository.save(servingOrder);
            menuOrderRepository.saveAll(menuOrders);
            return menuOrders;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
