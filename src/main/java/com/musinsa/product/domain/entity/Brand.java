package com.musinsa.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "brand")
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

    public String toString() {
        return "Brand(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

}
