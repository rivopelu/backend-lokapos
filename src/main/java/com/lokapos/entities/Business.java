package com.lokapos.entities;


import jakarta.persistence.*;
import lombok.*;

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

    @JoinColumn(name = "province_id")
    @ManyToOne
    private Province province;

    @JoinColumn(name = "city_id")
    @ManyToOne
    private City city;


    @JoinColumn(name = "district_id")
    @ManyToOne
    private District district;


    @JoinColumn(name = "sub_district_id")
    @ManyToOne
    private SubDistrict subDistrict;

    @Column(name = "is_active_subscription")
    private Boolean isActiveSubscription;

    @Column(name = "subscription_expire_date")
    private Long subscriptionExpireDate;

    @Column(name = "logo")
    private String logo;

}
