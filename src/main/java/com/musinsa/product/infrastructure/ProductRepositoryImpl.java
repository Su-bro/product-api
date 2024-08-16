package com.musinsa.product.infrastructure;

import com.musinsa.product.domain.repository.ProductRepositoryCustom;
import com.musinsa.product.domain.entity.QProduct;
import com.musinsa.product.dto.ProductByBrandResponse.ProductResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ProductResponse> findLowestPriceProductsByBrandGroupByCategory(int brandId) {
        QProduct product = QProduct.product;
        return queryFactory
            .select(Projections.constructor(ProductResponse.class,
                product.category.name.as("categoryName"),
                product.price.min().as("price")))
            .from(product)
            .where(product.brand.id.eq(brandId))
            .groupBy(product.category)
            .fetch();
    }

}
