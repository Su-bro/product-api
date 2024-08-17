package com.musinsa.product.application;

import com.musinsa.common.exception.DuplicateBrandException;
import com.musinsa.common.util.MessageUtil;
import com.musinsa.product.domain.entity.Brand;
import com.musinsa.product.domain.repository.BrandRepository;
import com.musinsa.product.dto.BrandDto.RegisterResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public RegisterResponse registerBrand(String brandName, String brandDesc) {
        brandRepository.findByName(brandName).ifPresent(brand -> {
            throw new DuplicateBrandException(MessageUtil.getMsg("E005"));
        });
        brandRepository.save(new Brand(brandName, brandDesc));
        return new RegisterResponse(MessageUtil.getMsg("M004"));
    }

    @Transactional
    public RegisterResponse updateBrand(int brandId, String brandName, String brandDesc) {
        brandRepository.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException(MessageUtil.getMsg("E002")))
            .update(brandName, brandDesc);
        return new RegisterResponse(MessageUtil.getMsg("M005"));
    }
}
