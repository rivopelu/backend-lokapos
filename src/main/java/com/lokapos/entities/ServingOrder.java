package com.lokapos.entities;


import com.lokapos.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "serving_order")
public class ServingOrder extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ORDER_STATUS_ENUM status;

    @Column(name = "total_transaction")
    private BigInteger totalTransaction;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private ORDER_PAYMENT_METHOD_ENUM paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private ORDER_PAYMENT_STATUS_ENUM paymentStatus;

    @Column(name = "total_item")
    private BigInteger totalItem;

    @JoinColumn(name = "business")
    @ManyToOne
    private Business business;

    @Column(name = "order_type")
    private ORDER_TYPE_ENUM orderType;

    @Column(name = "platform")
    private ORDER_PLATFORM_ENUM platform;

    @Column(name = "code")
    private String code;




}
