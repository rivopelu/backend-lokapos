package com.lokapos.entities;


import com.lokapos.enums.SUBSCRIPTION_ORDER_STATUS_ENUM;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_order")
public class SubscriptionOrder extends BaseEntity {

    @JoinColumn(name = "subscription_package_id")
    @ManyToOne
    private SubscriptionPackage subscriptionPackage;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;

    @Column(name = "total_transaction")
    private BigInteger totalTransaction;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SUBSCRIPTION_ORDER_STATUS_ENUM status;


}
