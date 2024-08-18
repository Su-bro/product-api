package com.musinsa.product.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
class BrandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void registerBrandSuccessTest() throws Exception {
        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandName\": \"무탠다드\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M004")));
    }

    @Test
    void registerBrandFailByDuplicateBrandNameTest() throws Exception {
        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandName\": \"무탠다드2\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드2\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M004")));

        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandName\": \"무탠다드2\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드2\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("E005")));
    }

    @Test
    void registerBrandFailByEmptyBrandNameTest() throws Exception {
        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("브랜드명을 입력해 주세요."));

        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandName\": \"무\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("브랜드명은 2글자 이상 20글자 이하로 입력 가능합니다."));

        mockMvc.perform(
                post("/api/brands")
                    .content("{\"brandName\": \"무탠다드!\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("브랜드명은 한글, 영문, 숫자만 입력 가능합니다."));
    }

    @Test
    void updateBrandSuccessTest() throws Exception {
        mockMvc.perform(
                put("/api/brands")
                    .content("{\"brandId\": 1, \"brandName\": \"무탠\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M005")));
    }

    @Test
    void updateBrandFailByEmptyBrandIdTest() throws Exception {
        mockMvc.perform(
                put("/api/brands")
                    .content("{\"brandId\": -1,\"brandName\": \"무탠\", \"brandDesc\": \"여름엔 쿨탠다드 겨울엔 힛탠다드\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("브랜드를 찾을 수 없습니다."));
    }

    @Test
    void deleteBrandSuccessTest() throws Exception {
        mockMvc.perform(
                delete("/api/brands/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(MessageUtil.getMsg("M006")));
    }

    @Test
    void deleteBrandFailByEmptyBrandIdTest() throws Exception {
        mockMvc.perform(
                delete("/api/brands/-1"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("브랜드를 찾을 수 없습니다."));
    }

}
