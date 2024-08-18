package com.musinsa.product.domain.repository;

import com.musinsa.product.domain.entity.Product;
import jakarta.transaction.Transactional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired BrandRepository brandRepository;

    @Test
    void findTopByCategoryAndIsDeletedFalseOrderByPriceAscReturnsCheapestProduct() {
        var category = categoryRepository.findByName("상의").orElseThrow();
        Optional<Product> result = productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceAsc(category);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get().isDeleted()).isFalse();
            softly.assertThat(result.get().getCategory().getName()).isEqualTo(category.getName());
            softly.assertThat(result.get().getPrice()).isEqualTo(10000);
        });
    }

    @Test
    void findTopByCategoryAndIsDeletedFalseOrderByPriceDescReturnsMostExpensiveProduct() {
        var category = categoryRepository.findByName("상의").orElseThrow();
        Optional<Product> result = productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceDesc(category);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get().isDeleted()).isFalse();
            softly.assertThat(result.get().getCategory().getName()).isEqualTo(category.getName());
            softly.assertThat(result.get().getPrice()).isEqualTo(11400);
        });
    }

    @Test
    void findByIdAndIsDeletedFalseReturnsCorrectProduct() {
        Optional<Product> result = productRepository.findByIdAndIsDeletedFalse(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findByIdAndIsDeletedFalseReturnsEmptyForDeletedProduct() {
        Optional<Product> result = productRepository.findByIdAndIsDeletedFalse(999L);
        assertFalse(result.isPresent());
    }

}
