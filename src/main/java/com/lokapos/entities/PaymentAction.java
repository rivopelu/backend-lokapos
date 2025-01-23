package com.lokapos.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_action")
public class PaymentAction extends BaseEntity {

    @JoinColumn(name = "serving_order_id")
    @ManyToOne
    private ServingOrder servingOrder;

    @Column(name = "name")
    private String name;

    @Column(name = "method")
    private String method;

    @Column(name = "url")
    private String url;

    @Column(name = "expire_time")
    private Long expireTime;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "transaction_id")
    private String transactionId;


}
