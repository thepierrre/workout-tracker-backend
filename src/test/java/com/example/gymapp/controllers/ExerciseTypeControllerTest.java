package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.CategoryDataHelper;
import com.example.gymapp.helpers.ExerciseTypeDataHelper;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.helpers.UserDataHelper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.ExerciseTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseTypeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ExerciseTypeService exerciseTypeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ExerciseTypeController exerciseTypeController;

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

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        exerciseTypeRepository.deleteAll();
    }

    @Test
    void testFindAll() {
    }

    @Test
    void testFindAllForUser() {
    }

    @ParameterizedTest
    @MethodSource("provideCreateExerciseTypePayloadAndExpectedResults")
    void testCreateExerciseType(String testCase, String input, String output, String message, int errorCode, boolean isSuccess) throws Exception {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));

        ExerciseTypeDto exerciseTypeDto = objectMapper.readValue(input, ExerciseTypeDto.class);

        if (isSuccess) {
            ExerciseTypeDto returnedDto = objectMapper.readValue(output, ExerciseTypeDto.class);
            when(exerciseTypeService
                    .createExercise(exerciseTypeDto, "user1"))
                    .thenReturn(returnedDto);
        }

        var resultActions = mvc.perform(post("/api/exercise-types").with(user("jack890"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        if (isSuccess) {
            resultActions.andExpect(jsonPath("$.name").value("dumbbell pushes"))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        } else {
            resultActions.andExpect(status().is(errorCode))
                    .andExpect(content().string(containsString(message)));
        }

        resultActions.andDo(result -> System.out.println(testCase));

    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testDeleteAll() {
    }

    @Test
    void testUpdateById() {
    }

    private static Stream<Arguments> provideCreateExerciseTypePayloadAndExpectedResults() {
        return Stream.of(
                Arguments.of(
                        "OK",
                        """
                            {
                                "name": "dumbbell pushes"
                            }
                        """,
                        """
                            {
                                "id": "14b6c95e-5828-4c9c-8e28-3d7f6a3c9a6d",
                                "name": "dumbbell pushes",
                                "categories": []
                            }
                        """,
                        "",
                        201,
                        true
                )
        );
    }
}

