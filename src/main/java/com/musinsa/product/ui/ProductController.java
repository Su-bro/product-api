package com.musinsa.product.ui;

import com.musinsa.product.application.ProductService;
import com.musinsa.product.dto.ProductByBrandResponse;
import com.musinsa.product.dto.ProductByCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "상품", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/lowest-price-by-category")
    @Operation(summary = "카테고리별 최저가격 상품 조회", description = "카테고리별 최저가격 상품을 조회합니다.")
    public ResponseEntity<ProductByCategoryResponse> getLowestPriceProductByCategory() {
        return ResponseEntity.ok(productService.getLowestPriceProductByCategory());
    }

    @GetMapping("/lowest-price-by-brand")
    @Operation(summary = "브랜드 최저가격 상품 조회", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품을 조회합니다.")
    public ResponseEntity<ProductByBrandResponse> getLowestPriceProductByBrand() {
        return ResponseEntity.ok(productService.getLowestPriceProductByBrand());
    }

}
