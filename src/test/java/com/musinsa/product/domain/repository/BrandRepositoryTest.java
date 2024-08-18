package com.musinsa.product.domain.repository;

import com.musinsa.product.domain.LowestPriceBrandProjection;
import com.musinsa.product.domain.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void findLowestPriceBrandWithAllCategoriesReturnsCorrectBrand() {
        Optional<LowestPriceBrandProjection> result = brandRepository.findLowestPriceBrandWithAllCategories();
        assertTrue(result.isPresent());
        assertEquals("D", result.get().getBrandName());
    }

    @Test
    void findByNameReturnsCorrectBrand() {
        Optional<Brand> result = brandRepository.findByName("A");
        assertTrue(result.isPresent());
        assertEquals("A", result.get().getName());
    }

    @Test
    void findByIdAndIsDeletedFalseReturnsCorrectBrand() {
        Optional<Brand> result = brandRepository.findByIdAndIsDeletedFalse(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void findByNameAndIsDeletedFalseReturnsCorrectBrand() {
        Optional<Brand> result = brandRepository.findByNameAndIsDeletedFalse("A");
        assertTrue(result.isPresent());
        assertEquals("A", result.get().getName());
    }

    @Test
    void findByNameReturnsEmptyForNonExistentBrand() {
        Optional<Brand> result = brandRepository.findByName("NonExistentBrand");
        assertFalse(result.isPresent());
    }

    @Test
    void findByIdAndIsDeletedFalseReturnsEmptyForDeletedBrand() {
        Optional<Brand> result = brandRepository.findByIdAndIsDeletedFalse(999);
        assertFalse(result.isPresent());
    }

    @Test
    void findByNameAndIsDeletedFalseReturnsEmptyForDeletedBrand() {
        brandRepository.findByName("F").ifPresent(brand -> {
            brand.delete();
            brandRepository.save(brand);
        });
        Optional<Brand> result = brandRepository.findByNameAndIsDeletedFalse("F");
        assertFalse(result.isPresent());
    }
}
