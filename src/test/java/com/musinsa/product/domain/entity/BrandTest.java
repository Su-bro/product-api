package com.musinsa.product.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    void constructorWithNameSetsName() {
        Brand brand = new Brand("무탠다드");
        assertEquals("무탠다드", brand.getName());
    }

    @Test
    void constructorWithNameAndDescSetsNameAndDesc() {
        Brand brand = new Brand("무탠다드", "쿨탠다드");
        assertEquals("무탠다드", brand.getName());
        assertEquals("쿨탠다드", brand.getDesc());
    }

    @Test
    void defaultConstructorSetsIsDeletedToFalse() {
        Brand brand = new Brand("무탠다드");
        assertFalse(brand.isDeleted());
    }

    @Test
    void updateChangesNameAndDesc() {
        Brand brand = new Brand("무탠다드", "쿨탠다드");
        brand.update("힛탠다드", "100원이벤트");
        assertEquals("힛탠다드", brand.getName());
        assertEquals("100원이벤트", brand.getDesc());
    }

    @Test
    void deleteSetsIsDeletedToTrue() {
        Brand brand = new Brand("무탠다드");
        brand.delete();
        assertTrue(brand.isDeleted());
    }

}
