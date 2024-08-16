package com.musinsa.product.domain.repository;

import com.musinsa.product.domain.entity.Category;
import com.musinsa.product.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findTopByCategoryOrderByPriceAsc(Category category);
}
