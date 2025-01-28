package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shift_account")
public class ShiftAccount {

    @Id
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "shift_id")
    @ManyToOne
    private Shift shift;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
