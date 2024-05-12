package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.helpers.UserTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @ParameterizedTest
    @MethodSource("provideCreateUserPayloadAndExpectedOutputs")
    public void testThatCreateUserSuccessfullyReturnsHttp201Created(int expectedHttpStatus, UserDto userDto, String requestBody) throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedHttpStatus));
    }


    private static Stream<Arguments> provideCreateUserPayloadAndExpectedOutputs() {
        return Stream.of(Arguments.of(
                201,
                UserTestDataHelper.createUserDto("Max", "max@gmail.com", "pass"),
                """
                    {
                        "username": "Max",
                        "password": "pass",
                        "email": "max@gmail.com"
                    }
                """
                )

        );
    }

//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public UserServiceImpl userServiceImpl() {
//            UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
//            when(userServiceImpl.createUser(any(UserDto.class))).thenAnswer(invocation -> {
//                UserDto userDto = invocation.getArgument(0);
//                // Assume user is saved in database and ID is generated
//                UserDto userWithId = UserDto.builder()
//                        .id(UUID.randomUUID()) // Generate random ID
//                        .username(userDto.getUsername())
//                        .email(userDto.getEmail())
//                        .password(userDto.getPassword()) // You might want to encode the password here
//                        .build();
//                return userWithId;
//            });
//            return userServiceImpl;
//        }
//    }
//
//    // Encode the password using PasswordEncoder
//    private String encodePassword(String password) {
//        return passwordEncoder.encode(password);
//    }

}
