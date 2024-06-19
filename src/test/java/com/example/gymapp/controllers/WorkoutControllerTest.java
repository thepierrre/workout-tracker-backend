package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WorkoutControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WorkoutService workoutService;

    @MockBean
    UserRepository userRepository;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }


    @Test
    void findAllForUser_Success() throws Exception {
        when(workoutService.findAllForUser(any(String.class)))
                .thenReturn(List.of(testData.workoutResponseDto1, testData.workoutResponseDto2));

        mvc.perform(get("/api/user-workouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].routineName", is("routine1")))
                .andExpect(jsonPath("$.[1].routineName", is("routine2")));
    }

    @Test
    void findAllForUser_UserNotFound() throws Exception {
        when(workoutService.findAllForUser("user1"))
                .thenThrow(new UsernameNotFoundException(
                        "User with the username \"user1\" not found."));

        mvc.perform(get("/api/user-workouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "User with the username \"user1\" not found."));
    }

    @Test
    void createWorkout_Success() throws Exception {
        String jsonInput = objectMapper.writeValueAsString(testData.workoutRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));

        when(workoutService.createWorkout(any(WorkoutDto.class), any(String.class)))
                .thenReturn(testData.workoutResponseDto1);

        mvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routineName", is("routine1")));
    }

    @Test
    void createWorkout_UserNotFound() throws Exception {
        String jsonInput = objectMapper.writeValueAsString(testData.workoutRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(workoutService.createWorkout(any(WorkoutDto.class), any(String.class)))
                .thenThrow(new UsernameNotFoundException(
                        "User with the username \"user1\" not found."));

        mvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with the username \"user1\" not found."));
    }

    @Test
    void createWorkout_RoutineNameNotFound() throws Exception {
        String jsonInput = objectMapper.writeValueAsString(testData.workoutRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(workoutService.createWorkout(any(WorkoutDto.class), any(String.class)))
                .thenThrow(new EntityNotFoundException(
                        String.format("Routine with the name \"%s\" not found for user \"user1\".", testData.workoutRequestDto1.getRoutineName())));

        mvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Routine with the name \"routine1\" not found for user \"user1\"."));
    }

    @Test
    void deleteById_Success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing()
                .when(workoutService).deleteById(any());

        mvc.perform(delete("/api/workouts/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void deleteById_IdNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new EntityNotFoundException(
                String.format("Workout with the ID %s not found.", id.toString())))
                .when(workoutService).deleteById(any());

        mvc.perform(delete("/api/workouts/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Workout with the ID " + id + " not found."));
    }
}