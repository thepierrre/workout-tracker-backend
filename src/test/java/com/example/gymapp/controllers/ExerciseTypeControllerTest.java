package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.CategoryDataHelper;
import com.example.gymapp.helpers.ExerciseTypeDataHelper;
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

    private static CategoryEntity category1;
    private static CategoryEntity category2;
    private static CategoryEntity category3;
    private static ExerciseTypeEntity exerciseType1;
    private static ExerciseTypeEntity exerciseType2;

    private static UserEntity user1;
    private static UserEntity user2;

    @BeforeAll
    static void setUpEntities() {
        category1 = CategoryDataHelper.createCategoryEntity("hamstrings", UUID.fromString("2d80a3b1-7c0e-4c3d-b82b-6851b44b4a17"));
        category2 = CategoryDataHelper.createCategoryEntity("glutes", UUID.fromString("a7c4f5d6-bc14-4e5a-80b1-71e09efbf3ff"));
        category3 = CategoryDataHelper.createCategoryEntity("lower back", UUID.fromString("d1f5e8a2-74f6-46ea-9ae3-763a2bf7a1b6"));

        exerciseType1 = ExerciseTypeDataHelper.createExerciseTypeEntity("bench press", UUID.fromString("e6f7c123-05e3-477b-8cf7-72a6e9f90521"));
        exerciseType2 = ExerciseTypeDataHelper.createExerciseTypeEntity("biceps curls", UUID.fromString("3e6a5d2b-cc68-4071-9d57-3c5a4d3a6264"));

        user1 = UserDataHelper.createUserEntity("jack890", "jack@abc.com", "p@ss");
        user2 = UserDataHelper.createUserEntity("kate123", "kate123@abc.com", "pswd");
    }

    // export to a separate class at a later point
    @BeforeEach
    void setUp() {
//        when(userRepository.findByUsername("jack890")).thenReturn(Optional.of(user1));
//        when(userRepository.findByUsername("kate123")).thenReturn(Optional.of(user2));
//
//        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        when(exerciseTypeRepository.save(any(ExerciseTypeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        categoryRepository.save(category1);
//        categoryRepository.save(category2);
//        categoryRepository.save(category3);
//        userRepository.save(user1);
//        userRepository.save(user2);

        user1.setExerciseTypes(new ArrayList<>(List.of(exerciseType1)));
        user2.setExerciseTypes(new ArrayList<>());

        when(userRepository.findByUsername("jack890")).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername("kate123")).thenReturn(Optional.of(user2));

        when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        when(categoryRepository.findById(category2.getId())).thenReturn(Optional.of(category2));
        when(categoryRepository.findById(category3.getId())).thenReturn(Optional.of(category3));

        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(exerciseTypeRepository.save(any(ExerciseTypeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        userRepository.save(user1);
        userRepository.save(user2);

//        when(categoryRepository.save(category1)).thenReturn(category1);
//        when(categoryRepository.save(category2)).thenReturn(category2);
//        when(categoryRepository.save(category3)).thenReturn(category3);
//
//        when(userRepository.findByUsername("jack890")).thenReturn(Optional.of(user1));
//        when(userRepository.findByUsername("kate123")).thenReturn(Optional.of(user2));
//
//        when(userRepository.save(user1)).thenReturn(user1);
//        when(userRepository.save(user2)).thenReturn(user2);
//
//        when(exerciseTypeRepository.save(exerciseType1)).thenReturn(exerciseType1);
//        when(exerciseTypeRepository.save(exerciseType2)).thenReturn(exerciseType2);
//
//        categoryRepository.save(category1);
//        categoryRepository.save(category2);
//        categoryRepository.save(category3);
//        userRepository.save(user1);
//        userRepository.save(user2);
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

        ExerciseTypeDto exerciseTypeDto = objectMapper.readValue(input, ExerciseTypeDto.class);

        if (isSuccess) {
            ExerciseTypeDto returnedDto = objectMapper.readValue(output, ExerciseTypeDto.class);
            when(exerciseTypeService
                    .createExercise(exerciseTypeDto, "jack890"))
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

