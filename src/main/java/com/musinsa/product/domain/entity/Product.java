package com.musinsa.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Product() {
    }

    public Product(String name, int price, Brand brand, Category category) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.category = category;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public Category getCategory() {
        return this.category;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ", brand=" + this.getBrand() + ", category=" + this.getCategory() + ")";
    }

}
