package com.lokapos.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@DynamicUpdate
public class BaseEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "active", columnDefinition = "TINYINT default 1")
    private Boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deletedDate")
    private Long deletedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Long updatedDate;


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.active = true;
        this.createdDate = new Date().getTime();
        this.updatedDate = new Date().getTime();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = new Date().getTime();
    }

    @PreRemove
    public void preRemove() {
        this.deletedDate = new Date().getTime();
        this.active = false;
    }

}

