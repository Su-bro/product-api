package com.musinsa.product.application;

import com.musinsa.product.domain.BrandRepository;
import com.musinsa.product.domain.CategoryRepository;
import com.musinsa.product.domain.ProductRepository;
import com.musinsa.product.dto.ProductByCategoryResponse;
import com.musinsa.product.dto.ProductByCategoryResponse.ProductResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }


    /**
     * 카테고리별 최저가격 브랜드와 상품 가격, 총액을 조회합니다.
     *
     * @return 최저가격 상품 목록과 총액
     */
    public ProductByCategoryResponse getLowestPriceProductByCategory() {
        List<ProductResponse> productResponses = categoryRepository.findAllByOrderByIdAsc().stream()
            .map(productRepository::findTopByCategoryOrderByPriceAsc)
            .flatMap(Optional::stream)
            .map(product -> new ProductResponse(
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getPrice())
            ).toList();

        int totalPrice = productResponses.stream()
            .mapToInt(ProductResponse::price)
            .sum();

        return new ProductByCategoryResponse(productResponses, totalPrice);
    }

}
