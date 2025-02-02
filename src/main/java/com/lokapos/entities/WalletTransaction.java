package com.lokapos.entities;


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

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private WALLET_TRANSACTION_TYPE_ENUM type;

}
