package com.oliverchen.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliverchen.springbootmall.dao.UserDao;
import com.oliverchen.springbootmall.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void register_success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("XXX@123.com");
        userRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.e_mail", equalTo("XXX@123.com")));

        assertNotEquals(userDao.getUserByEmail("XXX@123.com").getPassword(),userRequest.getPassword());
    }
}
