package com.musinsa.product.application;

import static org.mockito.BDDMockito.given;

import com.musinsa.common.util.MessageUtil;
import com.musinsa.product.domain.entity.Brand;
import com.musinsa.product.domain.repository.BrandRepository;
import com.musinsa.product.domain.repository.ProductRepository;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private BrandService brandService;

    static final String BRAND_NAME_1 = "test_brand1";
    static final String BRAND_NAME_2 = "test_brand2";

    static Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand(BRAND_NAME_1);
    }

    @Test
    void registerBrandTest() {
        // Arrange
        given(brandRepository.findByName(BRAND_NAME_1)).willReturn(Optional.empty());

        // Act
        var response = brandService.registerBrand(BRAND_NAME_1, "");

        // Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.getMessage()).isEqualTo(MessageUtil.getMsg("M004"));
        });
    }

    @Test
    void updateBrandTest() {
        // Arrange
        given(brandRepository.findByIdAndIsDeletedFalse(1)).willReturn(Optional.of(brand));

        // Act
        var response = brandService.updateBrand(1, BRAND_NAME_2, "modified");

        // Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.getMessage()).isEqualTo(MessageUtil.getMsg("M005"));
            softly.assertThat(brand.getName()).isEqualTo(BRAND_NAME_2);
            softly.assertThat(brand.getDesc()).isEqualTo("modified");
        });
    }

    @Test
    void deleteBrandTest() {
        // Arrange
        given(brandRepository.findByIdAndIsDeletedFalse(1)).willReturn(Optional.of(brand));

        // Act
        var response = brandService.deleteBrand(1);

        // Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.getMessage()).isEqualTo(MessageUtil.getMsg("M006"));
            softly.assertThat(brand.isDeleted()).isTrue();
        });
    }


}
