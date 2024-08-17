package com.musinsa.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "brand", indexes = @Index(name = "idx_brand_is_deleted", columnList = "is_deleted"))
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Comment("브랜드명")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Comment("브랜드 설명")
    @Column(name = "desc", length = 500, nullable = false, columnDefinition = "varchar(500) default ''")
    private String desc;

    @Comment("삭제 여부")
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    protected Brand() {
    }

    public Brand(String name) {
        this.name = name;
    }

    public Brand(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void update(String brandName, String brandDesc) {
        this.name = brandName;
        this.desc = brandDesc;
    }

    public String toString() {
        return "Brand(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public void delete() {
        this.isDeleted = true;
    }
}
