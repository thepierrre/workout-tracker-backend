package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.naming.spi.StateFactory;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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
    void logout() throws Exception {
        mvc.perform(post("/api/auth/logout").with(user("jack890")))
                .andExpect(status().isOk());

        mvc.perform(get("/api/categories"))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource("provideLoginUserPayloadAndExpectedResults_Success")
    void loginUser_Success(String input, String message, int errorCode) throws Exception {

        final HttpServletResponse response = mock(HttpServletResponse.class);

        LoginDto loginDto = objectMapper.readValue(input, LoginDto.class);
        when(authService.login(any(LoginDto.class), any(HttpServletResponse.class)))
                .thenReturn("User \"mockUser\" logged in.");

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @MethodSource("provideRegisterUserPayloadAndExpectedResults_Success")
    void registerUser_Success(String input, String message, int errorCode) throws Exception {

        RegisterDto registerDto = objectMapper.readValue(input, RegisterDto.class);
        when(authService.register(any(RegisterDto.class)))
            .thenReturn("User \"mockUser\" registered.");

        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
        .andExpect(status().is(errorCode))
        .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"message\":\"User with the username \\\"user1\\\" already exists.\"}', 409"
    })
    void registerUser_UsernameAlreadyExists(String message, int errorCode) throws Exception {
        String input = """
                          {
                              "username": "user1",
                              "email": "firstname.lastname@example.com",
                              "password": "mypassword"
                          }
                        """;

        when(authService.register(any(RegisterDto.class)))
                .thenThrow(new ConflictException("User with the username \"user1\" already exists."));

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"message\":\"User with the email \\\"user1@example.com\\\" already exists.\"}', 409"
    })
    void registerUser_EmailAlreadyExists(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "username": "user1",
                              "email": "user1@example.com",
                              "password": "mypassword"
                          }
                        """;

        when(authService.register(any(RegisterDto.class)))
                .thenThrow(new ConflictException("User with the email \"user1@example.com\" already exists."));

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"username\":\"Username cannot be empty.\"}', 400"
    })
    void registerUser_MissingUsername(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "email": "user1@example.com",
                              "password": "mypassword"
                          }
                        """;

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"email\":\"Email address cannot be empty.\"}', 400"
    })
    void registerUser_MissingEmail(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "username": "user",
                              "password": "mypassword"
                          }
                        """;

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"password\":\"Password cannot be empty.\"}', 400"
    })
    void registerUser_MissingPassword(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "username": "user",
                              "email": "user1@example.com"
                          }
                        """;

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"username\":\"Username cannot be empty.\"}', 400"
    })
    void loginUser_MissingUsername(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "password": "mypassword"
                          }
                        """;

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'{\"password\":\"Password cannot be empty.\"}', 400"
    })
    void loginUser_MissingPassword(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "username": "user1"
                          }
                        """;

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    @ParameterizedTest
    @CsvSource({
            "'Invalid username or password.', 401"
    })
    void loginUser_InvalidUsernameOrPassword(String message, int errorCode) throws Exception {

        String input = """
                          {
                              "username": "user1",
                              "password": "password1"
                          }
                        """;

        when(authService.login(any(LoginDto.class), any(HttpServletResponse.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password."));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(message));

    }

    private static Stream<Arguments> provideLoginUserPayloadAndExpectedResults_Success() {
        return Stream.of(
                Arguments.of(
                        """
                            {
                            "username": "user1",
                            "password": "password1"
                            }
                        """,
                        "User \"mockUser\" logged in.",
                        200
                ),
                Arguments.of(
                        """
                            {
                            "username": "user2",
                            "password": "p@ssw0rd"
                            }
                        """,
                        "User \"mockUser\" logged in.",
                        200
                )
        );
    }

    private static Stream<Arguments> provideRegisterUserPayloadAndExpectedResults_Success() {
      return Stream.of(
              Arguments.of(
                        """
                          {
                              "username": "john43",
                              "email": "john.miller@gmail.com",
                              "password": "p@ssword"
                          }
                        """,
                        "User \"mockUser\" registered.",
                        200
              ),
              Arguments.of(
                        """
                          {
                              "username": "mary1",
                              "email": "mary1@gmail.com",
                              "password": "123"
                          }
                        """,
                        "User \"mockUser\" registered.",
                        200
              )
      );
    }
}