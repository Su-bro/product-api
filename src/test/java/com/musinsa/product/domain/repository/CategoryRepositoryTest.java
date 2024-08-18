package com.musinsa.product.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.musinsa.product.domain.entity.Category;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllByOrderByIdAscReturnsCorrectOrder() {
        List<Category> result = categoryRepository.findAllByOrderByIdAsc();
        assertFalse(result.isEmpty());
    }

    @Test
    void findByNameReturnsCorrectCategory() {
        Optional<Category> result = categoryRepository.findByName("상의");
        assertTrue(result.isPresent());
        assertEquals("상의", result.get().getName());
    }

    @Test
    void findByNameReturnsEmptyForNonExistentCategory() {
        Optional<Category> result = categoryRepository.findByName("없는카테고리");
        assertFalse(result.isPresent());
    }
}
