package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserResponseDto;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void getUserDetails() throws Exception {
        when(userService.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        testData.user1.setWorkouts(List.of(testData.workoutEntity1));
        testData.user1.setExerciseTypes(List.of(testData.exerciseTypeEntity2));
        testData.user1.setRoutines(List.of(testData.routineEntity1, testData.routineEntity3));

        mvc.perform(get("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user1")))
                .andExpect(jsonPath("$.email", is("user1@example.com")))
                .andExpect(jsonPath("$.exerciseTypes", hasSize(1)))
                .andExpect(jsonPath("$.exerciseTypes.[0].name", is("exercise2")))
                .andExpect(jsonPath("$.workouts", hasSize(1)))
                .andExpect(jsonPath("$.workouts.[0].routineName", is("routine1")))
                .andExpect(jsonPath("$.routines", hasSize(2)))
                .andExpect(jsonPath("$.routines.[0].name", is("routine1")))
                .andExpect(jsonPath("$.routines.[1].name", is("routine3")));
    }
}