package com.example.bci.web.controller;

import com.example.bci.config.SecurityConfig;
import com.example.bci.security.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloWorldController.class)
@Import({SecurityConfig.class, JwtProperties.class})
class HelloWorldControllerITest {

    @Autowired
    MockMvc mockMvc;

    @WithMockUser(username = "spring")
    @Test
    void shouldReturnOkWhenHelloWorld() throws Exception {

        mockMvc.perform(get("/")).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world"));
    }

    @Test
    void shouldReturnUnauthorizedWhenHelloWorldWithoutUser() throws Exception {
        mockMvc.perform(get("/")).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

}
