package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_order")
public class MenuOrder extends BaseEntity {
    @JoinColumn(name = "menu_id")
    @ManyToOne
    private ServingMenu menu;

    @JoinColumn(name = "serving_order_id")
    @ManyToOne
    private ServingOrder servingOrder;

    @Column(name = "quantity")
    private BigInteger quantity;

    @Column(name = "price_per_qty")
    private BigInteger pricePerQty;

    @Column(name = "total_price")
    private BigInteger totalPrice;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "merchant_id")
    @ManyToOne
    private Merchant merchant;


}
