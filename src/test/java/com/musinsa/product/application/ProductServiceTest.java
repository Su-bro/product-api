package com.musinsa.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.musinsa.common.util.MessageUtil;
import com.musinsa.product.domain.LowestPriceBrandProjection;
import com.musinsa.product.domain.entity.Brand;
import com.musinsa.product.domain.entity.Category;
import com.musinsa.product.domain.entity.Product;
import com.musinsa.product.domain.repository.ProductRepository;
import com.musinsa.product.domain.repository.CategoryRepository;
import com.musinsa.product.domain.repository.BrandRepository;
import com.musinsa.product.dto.PriceRangeResponse;
import com.musinsa.product.dto.ProductByBrandResponse;
import com.musinsa.product.dto.ProductByBrandResponse.ProductResponse;
import com.musinsa.product.dto.ProductByCategoryResponse;
import com.musinsa.product.dto.ProductDto;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ProductService productService;

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
    void getLowestPriceProductByCategoryTest() {
        // Arrange
        given(categoryRepository.findAllByOrderByIdAsc()).willReturn(List.of(category1, category2));
        given(productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceAsc(category1)).willReturn(Optional.of(product1));
        given(productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceAsc(category2)).willReturn(Optional.of(product2));

        // Act
        ProductByCategoryResponse result = productService.getLowestPriceProductByCategory();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.products()).hasSize(2);
    }

    @Test
    void getLowestPriceProductByBrandTest() {
        // Arrange
        given(brandRepository.findLowestPriceBrandWithAllCategories()).willReturn(
            Optional.of(new LowestPriceBrandProjectionImpl(brand1.getId(), brand1.getName(), 30000)));
        given(productRepository.findLowestPriceProductsByBrandGroupByCategory(brand1.getId())).willReturn(
            List.of(new ProductResponse(category1.getName(), product1.getPrice()),
                new ProductResponse(category2.getName(), product2.getPrice())));

        // Act
        ProductByBrandResponse result = productService.getLowestPriceProductByBrand();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.products()).hasSize(2);
        assertThat(result.totalPrice()).isEqualTo(30000);
        assertThat(result.brandName()).isEqualTo(BRAND_NAME_1);
    }

    static class LowestPriceBrandProjectionImpl implements LowestPriceBrandProjection {

        private final Integer brandId;
        private final String brandName;
        private final int totalPrice;

        public LowestPriceBrandProjectionImpl(int brandId, String brandName, int totalPrice) {
            this.brandId = brandId;
            this.brandName = brandName;
            this.totalPrice = totalPrice;
        }

        @Override
        public Integer getBrandId() {
            return brandId;
        }

        @Override
        public String getBrandName() {
            return brandName;
        }

        @Override
        public Integer getTotalPrice() {
            return totalPrice;
        }
    }

    @Test
    void getPriceRangeByCategoryTest() {
        // Arrange
        given(categoryRepository.findByName(CATEGORY_NAME_1)).willReturn(Optional.of(category1));
        given(productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceAsc(category1)).willReturn(Optional.of(product1));
        given(productRepository.findTopByCategoryAndIsDeletedFalseOrderByPriceDesc(category1)).willReturn(Optional.of(product2));

        // Act
        PriceRangeResponse result = productService.getPriceRangeByCategory(CATEGORY_NAME_1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.categoryName()).isEqualTo(CATEGORY_NAME_1);
        assertThat(result.minPriceProduct().brandName()).isEqualTo(BRAND_NAME_1);
        assertThat(result.minPriceProduct().price()).isEqualTo(10000);
        assertThat(result.maxPriceProduct().brandName()).isEqualTo(BRAND_NAME_2);
        assertThat(result.maxPriceProduct().price()).isEqualTo(20000);
    }

    @Test
    void registerProductTest() {
        // Arrange
        given(categoryRepository.findByName(CATEGORY_NAME_1)).willReturn(Optional.of(category1));
        given(brandRepository.findByNameAndIsDeletedFalse(BRAND_NAME_1)).willReturn(Optional.of(brand1));
        // Act
        ProductDto.RegisterResponse result = productService.registerProduct(PRODUCT_NAME_1, 10000, BRAND_NAME_1, CATEGORY_NAME_1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(MessageUtil.getMsg("M001"));
    }

    @Test
    void updateProductTest() {
        // Arrange
        given(productRepository.findByIdAndIsDeletedFalse(1L)).willReturn(Optional.of(product1));
        // Act
        ProductDto.UpdateResponse result = productService.updateProduct(1L, PRODUCT_NAME_2, 11000);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(MessageUtil.getMsg("M002"));
        assertThat(product1.getName()).isEqualTo(PRODUCT_NAME_2);
        assertThat(product1.getPrice()).isEqualTo(11000);
    }

    @Test
    void deleteProductTest() {
        // Arrange
        given(productRepository.findByIdAndIsDeletedFalse(1L)).willReturn(Optional.of(product1));
        // Act
        var result = productService.deleteProduct(1L);

        // Assert
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).isEqualTo(MessageUtil.getMsg("M003"));
            softly.assertThat(product1.isDeleted()).isTrue();
        });

    }

}
