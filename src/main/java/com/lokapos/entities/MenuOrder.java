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
    private ServingMenu menuId;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price_per_qty")
    private BigInteger pricePerQty;

    @Column(name = "total_price")
    private BigInteger totalPrice;

    @Column(name = "note")
    private String note;


}
