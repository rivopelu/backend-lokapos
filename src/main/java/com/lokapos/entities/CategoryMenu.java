package com.lokapos.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_menu")
public class CategoryMenu extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Integer count;

    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;


}
