package com.oliverchen.springbootmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliverchen.springbootmall.constant.ProductEnum;
import com.oliverchen.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Transactional
    public void createProduct() throws Exception {
        ProductRequest mockProduct = new ProductRequest();
        mockProduct.setProductName("RR");
        mockProduct.setCategory(ProductEnum.CAR);
        mockProduct.setPrice(2000000);
        mockProduct.setStock(1);
        mockProduct.setImageUrl("https://hips.hearstapps.com/hmg-prod/images/2024-rolls-royce-phantom-102-64bad70ba7661.jpg?crop=0.498xw:0.446xh;0.300xw,0.332xh&resize=1200:*");

        String json = objectMapper.writeValueAsString(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products").contentType("application/json").content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(201));
    }

    @Test
    public void getProductById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                get("/products/{productId}",1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.productName",equalTo("蘋果（澳洲）")));
    }
    @Test
    @Transactional
    public void updateProductTest() throws Exception {
        ProductRequest mockProduct = new ProductRequest();
        mockProduct.setProductName("RR");
        mockProduct.setCategory(ProductEnum.CAR);
        mockProduct.setPrice(2000000);
        mockProduct.setStock(3);
        mockProduct.setImageUrl("https://hips.hearstapps.com/hmg-prod/images/2024-rolls-royce-phantom-102-64bad70ba7661.jpg?crop=0.498xw:0.446xh;0.300xw,0.332xh&resize=1200:*");

        String json = objectMapper.writeValueAsString(mockProduct);

       RequestBuilder requestBuilder =  MockMvcRequestBuilders.put("/products/{productId}",1)
               .contentType("application/json").content(json);

       mockMvc.perform(requestBuilder)
               .andDo(print())
               .andExpect(jsonPath("$.stock",equalTo(3)))
               .andExpect(jsonPath("$.productName",equalTo("RR")));
    }

}