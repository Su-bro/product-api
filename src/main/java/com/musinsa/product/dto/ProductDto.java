package com.musinsa.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

    private ProductDto() {
    }

    @Schema(description = "상품 등록 요청")
    public static class RegisterRequest {

        @NotBlank(message = "상품명을 입력해 주세요.")
        @Schema(description = "상품 이름", example = "반팔티셔츠")
        private String productName;

        @NotNull(message = "상품 가격을 입력해 주세요.")
        @Min(value = 0, message = "올바른 가격을 입력해 주세요.")
        @Schema(description = "상품 가격", example = "10000")
        private Integer price;

        @NotBlank(message = "카테고리명을 입력해 주세요.")
        @Schema(description = "카테고리명", example = "상의")
        private String categoryName;

        @NotBlank(message = "브랜드명을 입력해 주세요.")
        @Schema(description = "브랜드명", example = "무탠다드")
        private String brandName;

        public String getProductName() {
            return productName;
        }

        public int getPrice() {
            return price;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getBrandName() {
            return brandName;
        }

    }

    @Schema(description = "상품 등록 응답")
    public static class RegisterResponse {

        @Schema(description = "상품 등록 결과", example = "상품 등록이 완료되었습니다.")
        private String message;

        public RegisterResponse() {
        }

        public RegisterResponse(String result) {
            this.message = result;
        }

        public String getMessage() {
            return message;
        }
    }

    @Schema(description = "상품 수정 요청")
    public static class UpdateRequest {

        @NotBlank(message = "상품명을 입력해 주세요.")
        @Schema(description = "상품 이름", example = "반팔티셔츠")
        private String productName;

        @NotNull(message = "상품 가격을 입력해 주세요.")
        @Min(value = 0, message = "올바른 가격을 입력해 주세요.")
        @Schema(description = "상품 가격", example = "10000")
        private Integer price;

        public UpdateRequest() {
        }

        public String getProductName() {
            return productName;
        }

        public int getPrice() {
            return price;
        }

    }

    @Schema(description = "상품 수정 응답")
    public static class UpdateResponse {

        @Schema(description = "상품 수정 결과", example = "상품 수정이 완료되었습니다.")
        private String message;

        public UpdateResponse() {
        }

        public UpdateResponse(String result) {
            this.message = result;
        }

        public String getMessage() {
            return message;
        }

    }

    @Schema(description = "상품 삭제 응답")
    public static class DeleteResponse {

        @Schema(description = "상품 삭제 결과", example = "상품 삭제가 완료되었습니다.")
        private String message;

        public DeleteResponse() {
        }

        public DeleteResponse(String result) {
            this.message = result;
        }

        public String getMessage() {
            return message;

        }
    }
}
