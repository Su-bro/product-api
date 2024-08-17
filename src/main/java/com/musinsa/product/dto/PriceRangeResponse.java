package com.musinsa.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PriceRangeResponse(
    @Schema(description = "카테고리 이름", example = "상의") String categoryName,
    @Schema(description = "최저가격 상품") ProductResponse minPriceProduct,
    @Schema(description = "최고가격 상품") ProductResponse maxPriceProduct) {

    public record ProductResponse(
        @Schema(description = "상품 브랜드명", example = "나이키") String brandName,
        @Schema(description = "상품 가격", example = "10000") int price) {
    }

}
