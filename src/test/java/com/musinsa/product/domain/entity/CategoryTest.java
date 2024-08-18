package com.musinsa.product.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void constructorWithNameSetsName() {
        Category category = new Category("TEST");
        assertEquals("TEST", category.getName());
    }


    @Test
    void getIdReturnsCorrectId() {
        Category category = new Category("TEST");
        assertEquals(0, category.getId());
    }

}
