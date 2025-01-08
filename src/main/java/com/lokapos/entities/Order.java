package com.lokapos.entities;


import com.lokapos.enums.ORDER_STATUS_ENUM;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ORDER_STATUS_ENUM status;

    @Column(name = "total_transaction")
    private BigInteger totalTransaction;

    @Column(name = "payment_method")
    private String paymentMethod;


}
