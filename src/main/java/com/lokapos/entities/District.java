package com.lokapos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "district")
public class District {

    @Id
    private BigInteger id;
    @Column(name = "city_id")
    private BigInteger cityId;

    @Column(name = "province_id")
    private BigInteger provinceId;

    @Column(name = "name")
    private String name;


}


