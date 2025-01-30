package com.lokapos.entities;


import com.lokapos.enums.NOTIFICATION_TYPE_ENUM;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification  {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NOTIFICATION_TYPE_ENUM type;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;

    @Column(name = "token")
    private String token;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

}

