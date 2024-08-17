package com.musinsa.product.ui;

import com.musinsa.product.application.BrandService;
import com.musinsa.product.dto.BrandDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bands")
@Tag(name = "Brand", description = "브랜드 API")
public class BrandController {
    // 생성 업데이트 삭제

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "브랜드 등록", description = "브랜드를 등록합니다.")
    @PostMapping
    public ResponseEntity<BrandDto.RegisterResponse> registerBrand(@RequestBody @Valid BrandDto.RegisterRequest request) {
        return ResponseEntity.ok(brandService.registerBrand(request.getBrandName(), request.getBrandDesc()));
    }


}
