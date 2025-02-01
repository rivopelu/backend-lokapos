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
@Table(name = "merchant")
public class Merchant extends BaseEntity {

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "address")
    private String address;

    @Column(name = "province_id")
    private BigInteger provinceId;

    @Column(name = "city_id")
    private BigInteger cityId;

    @Column(name = "district_id")
    private BigInteger districtId;

    @Column(name = "logo")
    private String logo;

    @Column(name = "sub_district_id")
    private BigInteger subDistrictId;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;
}
