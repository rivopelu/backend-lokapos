package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_notification_subscription")
public class TransactionNotificationSubscription extends BaseEntity {
    @Column(name = "transaction_time")
    private Long transactionTime;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "signature_key")
    private String signatureKey;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "masked_card")
    private String maskedCard;

    @Column(name = "gross_amount")
    private Double grossAmount;

    @Column(name = "fraud_status")
    private String fraudStatus;

    @Column(name = "eci")
    private String eci;

    @Column(name = "currency")
    private String currency;

    @Column(name = "channel_response_message")
    private String channelResponseMessage;

    @Column(name = "channel_response_code")
    private String channelResponseCode;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "bank")
    private String bank;

    @Column(name = "approval_code")
    private String approvalCode;

    @ManyToOne
    @JoinColumn(name = "subscription_order_id")
    private SubscriptionOrder subscriptionOrder;


}

