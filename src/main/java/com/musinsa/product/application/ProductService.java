package com.musinsa.product.application;

import com.musinsa.common.exception.CategoryNotFoundException;
import com.musinsa.product.domain.repository.BrandRepository;
import com.musinsa.product.domain.LowestPriceBrandProjection;
import com.musinsa.product.domain.repository.CategoryRepository;
import com.musinsa.product.domain.repository.ProductRepository;
import com.musinsa.product.dto.PriceRangeResponse;
import com.musinsa.product.dto.ProductByBrandResponse;
import com.musinsa.product.dto.ProductByBrandResponse.ProductResponse;
import com.musinsa.product.dto.ProductByCategoryResponse;
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
        List<ProductByCategoryResponse.ProductResponse> productResponses =
            categoryRepository.findAllByOrderByIdAsc().stream()
            .map(productRepository::findTopByCategoryOrderByPriceAsc)
            .flatMap(Optional::stream)
            .map(product -> new ProductByCategoryResponse.ProductResponse(
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getPrice())
            ).toList();

        int totalPrice = productResponses.stream()
            .mapToInt(ProductByCategoryResponse.ProductResponse::price)
            .sum();
        return new ProductByCategoryResponse(productResponses, totalPrice);
    }

    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품을 조회합니다.
     *
     * @return 최저가격 브랜드와 카테고리별 상품 목록 및 총액
     */
    public ProductByBrandResponse getLowestPriceProductByBrand() {
        LowestPriceBrandProjection lowestPriceBrandProjection = brandRepository.findLowestPriceBrandWithAllCategories();
        List<ProductResponse> products = productRepository.findLowestPriceProductsByBrandGroupByCategory(lowestPriceBrandProjection.getBrandId());
        return new ProductByBrandResponse(
            lowestPriceBrandProjection.getBrandName(),
            products,
            lowestPriceBrandProjection.getTotalPrice()
        );
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회합니다.
     *
     * @return 최저, 최고 가격 브랜드와 상품 가격
     */
    public PriceRangeResponse getPriceRangeByCategory(String categoryName) {
        var category = categoryRepository.findByName(categoryName).orElseThrow(() -> new CategoryNotFoundException(categoryName));
        var minPriceProduct = productRepository.findTopByCategoryOrderByPriceAsc(category).orElseThrow();
        var maxPriceProduct = productRepository.findTopByCategoryOrderByPriceDesc(category).orElseThrow();
        return new PriceRangeResponse(
            category.getName(),
            new PriceRangeResponse.ProductResponse(minPriceProduct.getBrand().getName(), minPriceProduct.getPrice()),
            new PriceRangeResponse.ProductResponse(maxPriceProduct.getBrand().getName(), maxPriceProduct.getPrice())
        );
    }
}
