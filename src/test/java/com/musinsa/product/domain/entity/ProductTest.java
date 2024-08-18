package com.musinsa.product.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructorWithAllFieldsSetsFieldsCorrectly() {
        Brand brand = new Brand("무탠다드");
        Category category = new Category("상의");
        Product product = new Product("크루넥 반팔", 15000, brand, category);
        assertEquals("크루넥 반팔", product.getName());
        assertEquals(15000, product.getPrice());
        assertEquals(brand, product.getBrand());
        assertEquals(category, product.getCategory());
    }

    @Test
    void defaultConstructorSetsIsDeletedToFalse() {
        Product product = new Product();
        assertFalse(product.isDeleted());
    }

    @Test
    void updateChangesNameAndPrice() {
        Product product = new Product("크루넥 반팔", 15000, new Brand("무탠다드"), new Category("상의"));
        product.update("크루넥 긴팔", 16000);
        assertEquals("크루넥 긴팔", product.getName());
        assertEquals(16000, product.getPrice());
    }

    @Test
    void deleteSetsIsDeletedToTrue() {
        Product product = new Product("크루넥 반팔", 15000, new Brand("무탠다드"), new Category("상의"));
        product.delete();
        assertTrue(product.isDeleted());
    }

}
