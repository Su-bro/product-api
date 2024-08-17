package com.musinsa.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BrandDto {

    private BrandDto() {
    }

    @Schema(description = "브랜드 등록 요청", name = "BrandDto.RegisterRequest")
    public static class RegisterRequest {

        @Schema(description = "브랜드 이름", example = "무탠다드")
        @NotBlank(message = "브랜드명을 입력해 주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "브랜드명은 한글, 영문, 숫자만 입력 가능합니다.")
        @Pattern(regexp = "^.{2,20}$", message = "브랜드명은 2글자 이상 20글자 이하로 입력 가능합니다.")
        private String brandName;

        @Schema(description = "브랜드 설명", example = "무신사 스탠다드(MUSINSA STANDARD)는 대한민국을 대표하는 온라인 패션 스토어 무신사가 전개하는 자체상표(Private Brand)입니다")
        private String brandDesc;

        public String getBrandName() {
            return brandName;
        }

        public String getBrandDesc() {
            return brandDesc;
        }

    }

    @Schema(description = "브랜드 등록 응답", name = "BrandDto.RegisterResponse")
    public static class RegisterResponse {

        @Schema(description = "브랜드 등록 결과", example = "브랜드 등록이 완료되었습니다.")
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

    @Schema(description = "브랜드 수정 요청", name = "BrandDto.UpdateRequest")
    public static class UpdateRequest {

        @Schema(description = "브랜드 ID", example = "1")
        private int brandId;

        @Schema(description = "브랜드 이름", example = "무탠다드")
        @NotBlank(message = "브랜드명을 입력해 주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "브랜드명은 한글, 영문, 숫자만 입력 가능합니다.")
        @Pattern(regexp = "^.{2,20}$", message = "브랜드명은 2글자 이상 20글자 이하로 입력 가능합니다.")
        private String brandName;

        @Schema(description = "브랜드 설명", example = "무신사 스탠다드(MUSINSA STANDARD)는 대한민국을 대표하는 온라인 패션 스토어 무신사가 전개하는 자체상표(Private Brand)입니다")
        private String brandDesc;

        public int getBrandId() {
            return brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public String getBrandDesc() {
            return brandDesc;
        }

    }

    @Schema(description = "브랜드 수정 응답", name = "BrandDto.UpdateResponse")
    public static class UpdateResponse {

        @Schema(description = "브랜드 수정 결과", example = "브랜드 수정이 완료되었습니다.")
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

    @Schema(description = "브랜드 삭제 응답", name = "BrandDto.DeleteResponse")
    public static class DeleteResponse {

        @Schema(description = "브랜드 삭제 결과", example = "브랜드 삭제가 완료되었습니다.")
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
