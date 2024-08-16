package com.musinsa.product.domain.repository;

import com.musinsa.product.dto.ProductByBrandResponse.ProductResponse;
import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductResponse> findLowestPriceProductsByBrandGroupByCategory(int brandId);

}
