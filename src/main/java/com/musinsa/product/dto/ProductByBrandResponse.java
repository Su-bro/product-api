package com.musinsa.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ProductByBrandResponse(
    @Schema(description = "브랜드명", example = "나이키") String brandName,
    @Schema(description = "카테고리별 상품 목록") List<ProductResponse> products,
    @Schema(description = "총액", example = "10000") int totalPrice) {

    public record ProductResponse(
        @Schema(description = "상품 카테고리명", example = "상의") String categoryName,
        @Schema(description = "상품 가격", example = "10000") int price) {
    }
}
