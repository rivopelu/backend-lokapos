package com.lokapos.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class Wallet extends BaseEntity {

    @Column(name = "balance")
    private Long balance;

}
