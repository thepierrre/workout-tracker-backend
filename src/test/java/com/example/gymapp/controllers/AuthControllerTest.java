package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    AuthService authService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }

//    @AfterEach
//    public void tearDown() {
//        userRepository.deleteAll();
//    }

    @ParameterizedTest
    @MethodSource("provideRegisterUserPayloadAndExpectedResults")
    void testRegisterUser(String input) throws Exception {
//        RegisterDto registerDto = RegisterDto.builder()
//                .username("blabla")
//                .email("bla@gmail.com")
//                .password("aabc123")
//                .build();

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isOk());

    }

    private static Stream<Arguments> provideRegisterUserPayloadAndExpectedResults() {

      return Stream.of(
              Arguments.of(
                      """
                              {
                              "username": "john",
                              "email": "john.miller@gmail.com",
                              "password": "p@ssword"
                              }
                              """
              ),
              Arguments.of(
                      """
                              {
                              "username": "john",
                              "email": "john.miller@gmail.com",
                              "password": "p@ssword"
                              }
                              """
              )
      );

    }

}