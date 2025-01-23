package com.lokapos.controllers;

import com.lokapos.controller.AuthController;
import com.lokapos.model.request.RequestSignUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    RequestSignUp reqSignUp;



    @BeforeEach
    public void setup() {
        reqSignUp = RequestSignUp.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("password")
                .build();

    }

    @Autowired
    private MockMvc mvc;

//
//    @Test
//    @Order(1)
//    public void saveEmployeeTest() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/auth/sign-up")
//                        .content(asJsonString(reqSignUp))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.success").isBoolean());
//
//    }
}
