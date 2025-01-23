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
@Table(name = "serving_menu")
public class ServingMenu extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigInteger price;

    @Column(name = "image")
    private String image;

    @JoinColumn(name = "category_menu_id")
    @ManyToOne
    private CategoryMenu categoryMenu;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;


}
