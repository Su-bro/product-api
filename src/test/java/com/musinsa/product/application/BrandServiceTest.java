package com.musinsa.product.application;

import static org.mockito.BDDMockito.given;

import com.musinsa.common.util.MessageUtil;
import com.musinsa.product.domain.entity.Brand;
import com.musinsa.product.domain.entity.Category;
import com.musinsa.product.domain.entity.Product;
import com.musinsa.product.domain.repository.BrandRepository;
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

    @InjectMocks
    private BrandService brandService;

    static final String CATEGORY_NAME_1 = "test_category1";
    static final String CATEGORY_NAME_2 = "test_category2";
    static final String BRAND_NAME_1 = "test_brand1";
    static final String BRAND_NAME_2 = "test_brand2";
    static final String PRODUCT_NAME_1 = "test_product1";
    static final String PRODUCT_NAME_2 = "test_product2";

    static Category category1;
    static Category category2;
    static Brand brand1;
    static Brand brand2;
    static Product product1;
    static Product product2;

    @BeforeEach
    void setUp() {
        category1 = new Category(CATEGORY_NAME_1);
        category2 = new Category(CATEGORY_NAME_2);
        brand1 = new Brand(BRAND_NAME_1);
        brand2 = new Brand(BRAND_NAME_2);
        product1 = new Product(PRODUCT_NAME_1, 10000, brand1, category1);
        product2 = new Product(PRODUCT_NAME_2, 20000, brand2, category2);
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


}
