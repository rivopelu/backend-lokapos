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
@Table(name = "business")
public class Business extends BaseEntity {
    @Column(name = "business_name")
    private String businessName;

    @Column(name = "address")
    private String address;

    @Column(name = "province_id")
    private BigInteger provinceId;

    @Column(name = "city_id")
    private BigInteger cityId;


    @Column(name = "district_id")
    private BigInteger districtId;


    @Column(name = "sub_district_id")
    private BigInteger subDistrictId;

    @Column(name = "is_active_subscription")
    private Boolean isActiveSubscription;

    @Column(name = "subscription_expire_date")
    private Long subscriptionExpireDate;

    @Column(name = "logo")
    private String logo;

}
