package com.lokapos.entities;


import com.lokapos.enums.PAYMENT_METHOD_TYPE_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_STATUS_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_TYPE_ENUM;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet_transaction")
public class WalletTransaction extends BaseEntity {

    @JoinColumn(name = "wallet_id")
    @ManyToOne
    private Wallet wallet;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private WALLET_TRANSACTION_TYPE_ENUM type;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WALLET_TRANSACTION_STATUS_ENUM status;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PAYMENT_METHOD_TYPE_ENUM paymentMethod;

}
