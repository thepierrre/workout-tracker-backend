package com.example.gymapp.controllers;

import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.services.RoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoutineControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RoutineService routineService;

    @MockBean
    RoutineRepository routineRepository;

    @ParameterizedTest
    @MethodSource("provideCreateRoutinePayloadAndExpectedResults_Success")
    void createTrainingRoutine_Success(String input, String message, int errorCode) throws Exception {

        mvc.perform(post("/api/categories").with(user("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string(containsString(message)));
      }

    @Test
    void findAll() {
      }

    @Test
    void findAllForUser() {
      }

    @Test
    void updateById() {
      }

    @Test
    void deleteById() {
      }

    @Test
    void deleteAll() {
      }

    @Test
    void handleValidationExceptions() {
      }

    private static Stream<Arguments> provideCreateRoutinePayloadAndExpectedResults_Success() {
        return Stream.of(
                Arguments.of(
                        """
                            "name": "routine1"
                        """,
                        "",
                        201
                )
        );
    }
}