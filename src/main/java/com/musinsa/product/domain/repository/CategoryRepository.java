package com.musinsa.product.domain.repository;

import com.musinsa.product.domain.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByOrderByIdAsc();

    Optional<Category> findByName(String categoryName);
}
