package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    AuthService authService;

    @MockBean
    UserRepository userRepository;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testLogout() throws Exception {

        mvc.perform(post("/api/auth/logout"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/categories"))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource("provideLoginUserPayloadAndExpectedResults")
    void testLoginUser(String testCase, String input, String message, int errorCode) throws Exception {

        final HttpServletResponse response = mock(HttpServletResponse.class);

        LoginDto loginDto = objectMapper.readValue(input, LoginDto.class);
        when(authService.login(any(LoginDto.class), any(HttpServletResponse.class)))
                .thenReturn(new ResponseEntity<>(message, errorCode == 200 ? HttpStatus.OK : HttpStatus.UNAUTHORIZED));

        mvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message))
                .andDo(result -> System.out.println(testCase));

    }

    @ParameterizedTest
    @MethodSource("provideRegisterUserPayloadAndExpectedResults")
    void testRegisterUser(String testCase, String input, String message, int errorCode) throws Exception {


        RegisterDto registerDto = objectMapper.readValue(input, RegisterDto.class);
        when(authService.register(any(RegisterDto.class)))
                .thenReturn(new ResponseEntity<>(message, errorCode == 200 ? HttpStatus.OK : HttpStatus.CONFLICT));

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message))
                .andDo(result -> System.out.println(testCase));

    }

    private static Stream<Arguments> provideLoginUserPayloadAndExpectedResults() {

        return Stream.of(
                Arguments.of(
                "OK",
                        """
                            {
                            "username": "user1",
                            "password": "password1"
                            }
                        """,
                        "",
                        200
                ),
                Arguments.of(
                        "OK",
                        """
                            {
                            "username": "user2",
                            "password": "password2"
                            }
                        """,
                        "",
                        200
                ),
                Arguments.of(
                        "missing username",
                        """
                            {
                            "username": "",
                            "password": "password2"
                            }
                        """,
                        "{\"username\":\"Username cannot be empty.\"}",
                        400
                ),
                Arguments.of(
                        "missing password",
                        """
                            {
                            "username": "user2",
                            "password": ""
                            }
                        """,
                        "{\"password\":\"Password cannot be empty.\"}",
                        400
                ),
                Arguments.of(
                        "invalid username",
                        """
                            {
                            "username": "non-existing",
                            "password": "password2"
                            }
                        """,
                        "Invalid username or password.",
                        401
                ),
                Arguments.of(
                        "invalid password",
                        """
                            {
                            "username": "user1",
                            "password": "incorrect"
                            }
                        """,
                        "Invalid username or password.",
                        401
                )
        );
    }

    private static Stream<Arguments> provideRegisterUserPayloadAndExpectedResults() {

      return Stream.of(
              Arguments.of(
                    "OK",
                        """
                          {
                              "username": "john43",
                              "email": "john.miller@gmail.com",
                              "password": "p@ssword"
                          }
                        """,
                        "User \"john43\" registered.",
                        200
              ),
              Arguments.of(
              "OK",
                        """
                          {
                              "username": "mary1",
                              "email": "mary1@gmail.com",
                              "password": "123"
                          }
                        """,
                        "User \"mary1\" registered.",
                        200
              ),
              Arguments.of(
                        "missing username",
                        """
                          {
                              "username": "",
                              "email": "mary1@gmail.com",
                              "password": "123"
                          }
                        """,
                      "{\"username\":\"Username cannot be empty.\"}",
                      400
              ),
              Arguments.of(
                      "missing email",
                      """
                        {
                            "username": "peter390",
                            "email": "",
                            "password": "p@55word"
                        }
                      """,
                      "{\"email\":\"Email address cannot be empty.\"}",
                      400
              ),
              Arguments.of(
                      "missing password",
                      """
                        {
                            "username": "peter390",
                            "email": "peter.schmidt@example.com",
                            "password": ""
                        }
                      """,
                      "{\"password\":\"Password cannot be empty.\"}",
                      400
              ),
              Arguments.of(
                      "username already exists",
                      """
                        {
                            "username": "user1",
                            "email": "firstname.lastname@example.com",
                            "password": "mypassword"
                        }
                      """,
                      "User with the username \"user1\" already exists.",
                      409
              ),
              Arguments.of(
                      "user with the e-mail already exists",
                      """
                        {
                            "username": "example-user",
                            "email": "user2@example.com",
                            "password": "mypassword"
                        }
                      """,
                      "User with the e-mail \"user2@example.com\" already exists.",
                      409
              )
      );

    }

}