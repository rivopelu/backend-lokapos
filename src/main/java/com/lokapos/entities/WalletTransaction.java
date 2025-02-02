package com.lokapos.entities;


import com.lokapos.enums.WALLET_TRANSACTION_STATUS_ENUM;
import com.lokapos.enums.WALLET_TRANSACTION_TYPE_ENUM;
import jakarta.persistence.*;
import lombok.*;

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

}
