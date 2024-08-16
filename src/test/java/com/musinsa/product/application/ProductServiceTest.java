package com.musinsa.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.musinsa.product.domain.Brand;
import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.domain.ProductRepository;
import com.musinsa.product.domain.CategoryRepository;
import com.musinsa.product.domain.BrandRepository;
import com.musinsa.product.dto.ProductByCategoryResponse;
import java.util.List;
import java.util.Optional;
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

    String categoryName1 = "test_category1";
    String categoryName2 = "test_category2";
    String brandName = "test_brand";
    String productName1 = "test_product1";
    String productName2 = "test_product2";

    @Test
    void getLowestPriceProductByCategoryTest() {
        // Arrange
        Category category1 = new Category(categoryName1);
        Category category2 = new Category(categoryName2);
        Brand brand = new Brand(brandName);
        Product product1 = new Product(productName1, 10000, brand, category1);
        Product product2 = new Product(productName2, 20000, brand, category2);
        given(categoryRepository.findAllByOrderByIdAsc()).willReturn(List.of(category1, category2));
        given(productRepository.findTopByCategoryOrderByPriceAsc(category1)).willReturn(Optional.of(product1));
        given(productRepository.findTopByCategoryOrderByPriceAsc(category2)).willReturn(Optional.of(product2));

        // Act
        ProductByCategoryResponse result = productService.getLowestPriceProductByCategory();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.products()).hasSize(2);
    }
}
