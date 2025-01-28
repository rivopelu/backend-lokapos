package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shift")
public class ShiftAccount {

    @Id
    private String id;

    @JoinColumn(name = "shift_id")
    @ManyToOne
    private Shift shift;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;
}
