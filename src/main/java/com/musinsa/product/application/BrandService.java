package com.musinsa.product.application;

import com.musinsa.common.exception.DuplicateBrandException;
import com.musinsa.common.exception.ResourceNotFoundException;
import com.musinsa.common.util.MessageUtil;
import com.musinsa.product.domain.entity.Brand;
import com.musinsa.product.domain.repository.BrandRepository;
import com.musinsa.product.domain.repository.ProductRepository;
import com.musinsa.product.dto.BrandDto.RegisterResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public BrandService(BrandRepository brandRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
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
        var brand = brandRepository.findByIdAndIsDeletedFalse(brandId)
            .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.getMsg("E002")));
        if (!brand.getName().equals(brandName)) {
            brandRepository.findByName(brandName).ifPresent(b -> {
                throw new DuplicateBrandException(MessageUtil.getMsg("E005"));
            });
        }
        brand.update(brandName, brandDesc);
        return new RegisterResponse(MessageUtil.getMsg("M005"));
    }

    @Transactional
    public RegisterResponse deleteBrand(int brandId) {
        var brand = brandRepository.findByIdAndIsDeletedFalse(brandId)
            .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.getMsg("E002")));
        productRepository.deleteAllByBrand(brand);
        brand.delete();
        return new RegisterResponse(MessageUtil.getMsg("M006"));
    }
}
