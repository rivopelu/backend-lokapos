package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shift")
public class Shift extends BaseEntity {


    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @JoinColumn(name = "merchant_id")
    @ManyToOne
    private Merchant merchant;

    @JoinColumn(name = "is_active")
    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "shift_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id"))
    Set<Account> accounts;


}
