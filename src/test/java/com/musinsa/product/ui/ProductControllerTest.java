package com.musinsa.product.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musinsa.common.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getLowestPriceProductByCategorySuccessTest() throws Exception {
        mockMvc.perform(
                get("/api/products/lowest-price-by-category"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalPrice").isNumber())
            .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    void getLowestPriceProductByBrandSuccessTest() throws Exception {
        mockMvc.perform(
                get("/api/products/lowest-price-by-brand"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.brandName").isString())
            .andExpect(jsonPath("$.totalPrice").isNumber())
            .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    void getPriceRangeByCategorySuccessTest() throws Exception {
        mockMvc.perform(
                get("/api/products/price-range")
                    .param("categoryName", "상의"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.categoryName").value("상의"))
            .andExpect(jsonPath("$.minPriceProduct.brandName").isString())
            .andExpect(jsonPath("$.minPriceProduct.price").isNumber())
            .andExpect(jsonPath("$.maxPriceProduct.brandName").isString())
            .andExpect(jsonPath("$.maxPriceProduct.price").isNumber());
    }

    @Test
    void getPriceRangeByCategoryFailByCategoryNameTest() throws Exception {
        mockMvc.perform(
                get("/api/products/price-range")
                    .param("categoryName", "없는 카테고리"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E003")));
    }

    @Test
    void registerProductSuccessTest() throws Exception {
        mockMvc.perform(
                post("/api/products")
                    .content("{\"productName\": \"상품명1\", \"price\": 100, \"brandName\": \"D\", \"categoryName\": \"상의\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M001")));
    }

    @Test
    void registerProductFailByCategoryNameTest() throws Exception {
        mockMvc.perform(
                post("/api/products")
                    .content("{\"productName\": \"상품명1\", \"price\": 100, \"brandName\": \"A\", \"categoryName\": \"없는 카테고리\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E003")));
    }

    @Test
    void registerProductFailByBrandNameTest() throws Exception {
        mockMvc.perform(
                post("/api/products")
                    .content("{\"productName\": \"상품명1\", \"price\": 100, \"brandName\": \"없는 브랜드\", \"categoryName\": \"상의\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E002")));
    }

    @Test
    void updateProductSuccessTest() throws Exception {
        mockMvc.perform(
                put("/api/products/1")
                    .content("{\"productName\": \"상품명1\", \"price\": 100}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M002")));
    }

    @Test
    void updateProductFailByProductIdTest() throws Exception {
        mockMvc.perform(
                put("/api/products/-1")
                    .content("{\"productName\": \"상품명1\", \"price\": 100}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E004")));
    }

    @Test
    void deleteProductSuccessTest() throws Exception {
        mockMvc.perform(
                delete("/api/products/4"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M003")));
    }

    @Test
    void deleteProductFailByProductIdTest() throws Exception {
        mockMvc.perform(
                delete("/api/products/-1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E004")));
    }

}
