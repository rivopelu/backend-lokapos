package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
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

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShiftAccount> shiftAccounts;


}
