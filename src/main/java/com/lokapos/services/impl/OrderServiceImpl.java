package com.lokapos.services.impl;

import com.lokapos.entities.Account;
import com.lokapos.entities.MenuOrder;
import com.lokapos.entities.ServingOrder;
import com.lokapos.entities.ServingMenu;
import com.lokapos.enums.*;
import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.request.RequestCreateOrder;
import com.lokapos.model.response.ResponseCheckOrderPaymentStatus;
import com.lokapos.model.response.ResponseCreateOrder;
import com.lokapos.model.response.ResponseDetailOrder;
import com.lokapos.model.response.ResponseListOrder;
import com.lokapos.repositories.MenuOrderRepository;
import com.lokapos.repositories.ServingOrderRepository;
import com.lokapos.repositories.ServingMenuRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.OrderService;
import com.lokapos.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.EntityUtils;
import utils.UtilsHelper;

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
        String businessId = accountService.getCurrentBusinessIdOrNull();
        ServingOrder findLatest = servingOrderRepository.findLatestData(businessId).orElse(null);
        BigInteger latestCode = null;
        if (findLatest != null) {
            latestCode = findLatest.getCode();
        }
        try {
            ServingOrder servingOrderBuilder = ServingOrder.builder()
                    .status(ORDER_STATUS_ENUM.PENDING)
                    .platform(req.getPlatform())
                    .orderType(req.getType())
                    .code(UtilsHelper.generateOrderCode(latestCode))
                    .paymentStatus(ORDER_PAYMENT_STATUS_ENUM.PENDING)
                    .paymentMethod(req.getPaymentMethod())
                    .build();

            if (req.getPaymentMethod().equals(ORDER_PAYMENT_METHOD_ENUM.CASH)) {
                servingOrderBuilder.setPaymentStatus(ORDER_PAYMENT_STATUS_ENUM.SUCCESS);
            }

            ServingOrder servingOrder = buildMenuOrders(req.getMenuList(), servingOrderBuilder);
            String qrisUrl = null;
            if (req.getPaymentMethod().equals(ORDER_PAYMENT_METHOD_ENUM.QRIS)) {
                qrisUrl = paymentService.createPaymentUsingEWallet(servingOrder);
            }

            return ResponseCreateOrder.builder()
                    .qrisUrl(qrisUrl)
                    .orderId(servingOrder.getId())
                    .paymentMethod(req.getPaymentMethod())
                    .paymentStatus(servingOrder.getPaymentStatus())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseCheckOrderPaymentStatus checkStatus(String orderId) {
        ORDER_PAYMENT_STATUS_ENUM orderPaymentStatusEnum = paymentService.checkStatusOrder(orderId);

        return ResponseCheckOrderPaymentStatus.builder()
                .paymentStatus(orderPaymentStatusEnum)
                .build();
    }

    @Override
    public Page<ResponseListOrder> getListOrder(Pageable pageable) {
        String businessId = accountService.getCurrentBusinessIdOrNull();
        try {
            Page<ServingOrder> servingOrderPage = servingOrderRepository.findAllByBusinessIdAndActiveTrueOrderByCreatedDateDesc(pageable, businessId);
            List<ResponseListOrder> responseListOrders = new ArrayList<>();
            for (ServingOrder servingOrder : servingOrderPage) {
                ResponseListOrder buildResponse = ResponseListOrder.builder()
                        .id(servingOrder.getId())
                        .status(servingOrder.getStatus())
                        .totalOrder(servingOrder.getTotalTransaction())
                        .totalItem(servingOrder.getTotalItem())
                        .paymentStatus(servingOrder.getPaymentStatus())
                        .type(servingOrder.getOrderType())
                        .code(servingOrder.getCode())
                        .platform(servingOrder.getPlatform())
                        .paymentMethod(servingOrder.getPaymentMethod())
                        .build();
                responseListOrders.add(buildResponse);
            }
            return new PageImpl<>(responseListOrders, pageable, servingOrderPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseDetailOrder getOrderDetail(String id) {
        ServingOrder servingOrder = servingOrderRepository.findById(id).orElseThrow(() -> new BadRequestException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
        List<MenuOrder> menuOrderList = menuOrderRepository.findAllByServingOrderId(servingOrder.getId());

        try {

            List<ResponseDetailOrder.MenuList> menuLists = new ArrayList<>();
            for (MenuOrder menuOrder : menuOrderList) {
                ResponseDetailOrder.MenuList menuList = ResponseDetailOrder.MenuList.builder()
                        .name(menuOrder.getMenu().getName())
                        .image(menuOrder.getMenu().getImage())
                        .id(menuOrder.getMenu().getId())
                        .quantity(menuOrder.getQuantity())
                        .build();
                menuLists.add(menuList);
            }

            return ResponseDetailOrder.builder()
                    .id(servingOrder.getId())
                    .status(servingOrder.getStatus())
                    .paymentStatus(servingOrder.getPaymentStatus())
                    .totalOrder(servingOrder.getTotalTransaction())
                    .totalItem(servingOrder.getTotalItem())
                    .paymentStatus(servingOrder.getPaymentStatus())
                    .type(servingOrder.getOrderType())
                    .paymentMethod(servingOrder.getPaymentMethod())
                    .code(servingOrder.getCode())
                    .platform(servingOrder.getPlatform())
                    .menuList(menuLists)
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String readyOrder(String id) {
        ServingOrder servingOrder = servingOrderRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.ORDER_NOT_FOUND.name()));
        servingOrder.setStatus(ORDER_STATUS_ENUM.READY);
        try {
            servingOrderRepository.save(servingOrder);
            return RESPONSE_ENUM.SUCCESS.name();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private ServingOrder buildMenuOrders(List<RequestCreateOrder.ListMenu> listMenus, ServingOrder servingOrder) {
        int index = 0;
        BigInteger totalTransaction = BigInteger.ZERO;
        BigInteger totalItem = BigInteger.ZERO;
        Account account = accountService.getCurrentAccount();
        if (account.getBusiness() == null) {
            throw new BadRequestException(RESPONSE_ENUM.ACCOUNT_DONT_HAVE_BUSINESS.name());
        }
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
                BigInteger calculateTotal = totalTransaction.add(totalPrice);
                BigInteger calculateTotalItem = totalItem.add(listMenu.getQuantity());
                totalTransaction = calculateTotal;
                totalItem = calculateTotalItem;
                menuOrders.add(menuOrder);
                EntityUtils.created(menuOrder, accountService.getCurrentAccountId());
            }


            EntityUtils.created(servingOrder, accountService.getCurrentAccountId());
            servingOrder.setTotalTransaction(totalTransaction);
            servingOrder.setTotalItem(totalItem);
            servingOrder.setBusiness(account.getBusiness());
            servingOrder = servingOrderRepository.save(servingOrder);
            menuOrderRepository.saveAll(menuOrders);
            return servingOrder;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
