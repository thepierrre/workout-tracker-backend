package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.ExerciseTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
class ExerciseTypeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ExerciseTypeService exerciseTypeService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExerciseTypeRepository exerciseTypeRepository;

    @MockBean
    RoleRepository roleRepository;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void createExerciseType_Success() throws Exception {
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.exerciseTypeRequestDto1);

        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));

        when(exerciseTypeService.createExercise(any(ExerciseTypeDto.class), any(String.class)))
                .thenReturn(testData.exerciseTypeResponseDto1);

        mvc.perform(post("/api/exercise-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated());
    };

    @Test
    void createExerciseType_UserNotFound() throws Exception {
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.exerciseTypeRequestDto1);

        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(exerciseTypeService.createExercise(any(ExerciseTypeDto.class), any(String.class)))
                .thenThrow(new UsernameNotFoundException(String.format(
                        "User with the username \"user1\" not found.")));

        mvc.perform(post("/api/exercise-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with the username \"user1\" not found."));
    };

    @Test
    void createExerciseType_ExerciseNameAlreadyExists() throws Exception {
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.exerciseTypeRequestDto1);

        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(exerciseTypeService.createExercise(any(ExerciseTypeDto.class), any(String.class)))
                .thenThrow(
                        new ConflictException(
                        "Exercise with the name '" + testData.exerciseTypeRequestDto1.getName() + "' already exists."));

        mvc.perform(post("/api/exercise-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isConflict())
                .andExpect(content().string(
                        "{\"message\":\"Exercise with the name 'exerciseType1' already exists.\"}"));
    };

    @Test
    void createExerciseType_CategoryNotFound() throws Exception {
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryResponseDto2);
        String jsonInput = objectMapper.writeValueAsString(testData.exerciseTypeRequestDto1);

        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto1);
        testData.exerciseTypeResponseDto1.getCategories().add(testData.categoryResponseDto2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        when(exerciseTypeService.createExercise(any(ExerciseTypeDto.class), any(String.class)))
                .thenThrow(
                        new EntityNotFoundException(
                                "Category with the ID " + testData.categoryResponseDto1.getId() + " not found."));

        mvc.perform(post("/api/exercise-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "Category with the ID " + testData.categoryResponseDto1.getId() + " not found."));
    };

    @Test
    void findAllForUser_Success() throws Exception {
        when(exerciseTypeService.findAllForUser(any(String.class)))
                .thenReturn(List.of(testData.exerciseTypeResponseDto1, testData.exerciseTypeResponseDto2));

        mvc.perform(get("/api/user-exercise-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("exerciseType1")))
                .andExpect(jsonPath("$.[1].name", is("exerciseType2")));
    }

    @Test
    void findAllForUser_UserNotFound() throws Exception {
        when(exerciseTypeService.findAllForUser("user1"))
                .thenThrow(new UsernameNotFoundException(
                        "User with the username \"user1\" not found."));

        mvc.perform(get("/api/user-exercise-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "User with the username \"user1\" not found."));
    }

    @Test
    void deleteById_Success() throws Exception {
    }

    @Test
    void deleteById_ExerciseTypeIdNotFound() throws Exception {
    }

    @Test
    void updateById_Success() throws Exception {
    }

    @Test
    void updateById_ExerciseTypeIdNotFound() throws Exception {
    }
}

