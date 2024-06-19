package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.RoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RoutineControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RoutineService routineService;

    @MockBean
    UserRepository userRepository;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void createRoutine_Success() throws Exception {
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.routineRequestDto1);

        testData.routineResponseDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto1);
        testData.routineResponseDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));

        when(routineService.createRoutine(any(RoutineDto.class), any(String.class)))
                .thenReturn(testData.routineResponseDto1);

        mvc.perform(post("/api/routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated());
    };

    @Test
    void createRoutine_UserNotFound() throws Exception {
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.routineRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(routineService.createRoutine(any(RoutineDto.class), any(String.class)))
                .thenThrow(new UsernameNotFoundException(
                        "User with the username \"user1\" not found."));

        mvc.perform(post("/api/routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with the username \"user1\" not found."));
    };

    @Test
    void createRoutine_RoutineNameAlreadyExists() throws Exception {
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.routineRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        when(routineService.createRoutine(any(RoutineDto.class), any(String.class)))
                .thenThrow(
                        new ConflictException(
                                "Routine with the name '" + testData.routineRequestDto1.getName() + "' already exists."));

        mvc.perform(post("/api/routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isConflict())
                .andExpect(content().string(
                        "{\"message\":\"Routine with the name 'routine1' already exists.\"}"));
    };

    @Test
    void createRoutine_ExerciseTypeNotFound() throws Exception {
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.routineRequestDto1);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        when(routineService.createRoutine(any(RoutineDto.class), any(String.class)))
                .thenThrow(
                        new EntityNotFoundException(
                                "Exercise type with the ID " + testData.exerciseTypeResponseDto1.getId() + " not found."));

        mvc.perform(post("/api/routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "Exercise type with the ID " + testData.exerciseTypeResponseDto1.getId() + " not found."));
    };

    @Test
    void findAllForUser_Success() throws Exception {
        when(routineService.findAllForUser(any(String.class)))
                .thenReturn(List.of(testData.routineResponseDto1, testData.routineResponseDto2));

        mvc.perform(get("/api/user-routines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("routine1")))
                .andExpect(jsonPath("$.[1].name", is("routine2")));
    };

    @Test
    void findAllForUser_UserNotFound() throws Exception {
        when(routineService.findAllForUser("user1"))
                .thenThrow(new UsernameNotFoundException(
                        "User with the username \"user1\" not found."));

        mvc.perform(get("/api/user-routines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "User with the username \"user1\" not found."));
    };

    @Test
    void updateById_Success() throws Exception {
        UUID id = UUID.randomUUID();

        when(routineService.updateById(any(), any(RoutineDto.class)))
                .thenReturn(testData.routineResponseDto1);

        RoutineDto input = testData.routineRequestDto1;
        String jsonInput = objectMapper.writeValueAsString(input);

        mvc.perform(put("/api/routines/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("routine1")));
    };

    @Test
    void updateById_IdNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(routineService.updateById(any(), any(RoutineDto.class)))
                .thenThrow(new UsernameNotFoundException(
                        String.format("Routine with the ID %s not found.", id.toString())));

        RoutineDto input = testData.routineRequestDto1;
        String jsonInput = objectMapper.writeValueAsString(input);

        mvc.perform(put("/api/routines/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(String.format("Routine with the ID %s not found.", id.toString())));
    };

    @Test
    void deleteById_Success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing()
                .when(routineService).deleteById(any());

        mvc.perform(delete("/api/routines/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    };

    @Test
    void deleteById_IdNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new EntityNotFoundException(
                String.format("Routine with the ID %s not found.", id.toString())))
                .when(routineService).deleteById(any());

        mvc.perform(delete("/api/routines/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Routine with the ID " + id + " not found."));
    };

}