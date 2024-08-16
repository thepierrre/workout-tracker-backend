package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.UserSettingsMapper;
import com.example.gymapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserSettingsMapper userSettingsMapper;

    TestDataInitializer.TestData testData;

    UserEntity user;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
        user = testData.user1;

        user.setWorkouts(List.of(testData.workoutEntity1));
        user.setExerciseTypes(List.of(testData.exerciseTypeEntity2));
        user.setRoutines(List.of(testData.routineEntity1, testData.routineEntity3));
        user.setUserSettings(testData.userSettingsEntity1);
        when(userService.findByUsername("user1")).thenReturn(Optional.of(user));
        when(userService.getUserSettings("user1")).thenReturn(testData.userSettingsResponseDto1);
        when(userService.updateUserSettings(any(String.class), any(UserSettingsDto.class))).thenReturn(testData.userSettingsResponseDto3);
    }

    @Test
    void getUserDetails() throws Exception {
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

    @Test
    void getUserSettings() throws Exception {
        mvc.perform(get("/api/users/user-settings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.changeThreshold", is(1.0)))
                .andExpect(jsonPath("$.weightUnit", is("kgs")));
    }

    @Test
    void updateUserSettings() throws Exception {
        UserSettingsDto input = testData.userSettingsRequestDto3;
        String jsonInput = objectMapper.writeValueAsString(input);

        mvc.perform(patch("/api/users/user-settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.changeThreshold", is(5.0)))
                .andExpect(jsonPath("$.weightUnit", is("lbs")));
    }
}