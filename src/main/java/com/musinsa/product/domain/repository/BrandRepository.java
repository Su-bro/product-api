package com.musinsa.product.domain.repository;

import com.musinsa.product.domain.LowestPriceBrandProjection;
import com.musinsa.product.domain.entity.Brand;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT b.id AS brand_id, b.name AS brand_name, SUM(mp.min_price) AS total_price
            FROM brand b
                     JOIN (SELECT p.brand_id, MIN(p.price) AS min_price, p.category_id
                           FROM product p
                           WHERE p.is_deleted = FALSE
                           GROUP BY p.brand_id, p.category_id) mp ON mp.brand_id = b.id
            WHERE b.is_deleted = false
            GROUP BY b.id
            HAVING COUNT(DISTINCT mp.category_id) = (SELECT COUNT(*) FROM category)
            ORDER BY SUM(mp.min_price)
            LIMIT 1
        """)
    Optional<LowestPriceBrandProjection> findLowestPriceBrandWithAllCategories();

    Optional<Brand> findByName(String brandName);

    Optional<Brand> findByIdAndIsDeletedFalse(int brandId);

    Optional<Brand> findByNameAndIsDeletedFalse(String brandName);
}
