package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ExerciseTypeServiceTest {

    @Autowired
    ExerciseTypeService exerciseTypeService;

    @MockBean
    ExerciseTypeRepository exerciseTypeRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ExerciseTypeMapper exerciseTypeMapper;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();

        UserEntity user1 = testData.user1;

        testData.exerciseTypeEntity1.getCategories().add(testData.categoryEntity1);
        testData.exerciseTypeEntity1.getCategories().add(testData.categoryEntity2);
        testData.exerciseTypeEntity2.getCategories().add(testData.categoryEntity1);
        testData.exerciseTypeEntity2.getCategories().add(testData.categoryEntity3);

        user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        user1.getExerciseTypes().add(testData.exerciseTypeEntity2);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        exerciseTypeRepository.deleteAll();

    }

    @Test
    void createExercise_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(exerciseTypeRepository.save(testData.exerciseTypeEntity3)).thenReturn(testData.exerciseTypeEntity3);
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto3)).thenReturn(testData.exerciseTypeEntity3);
        when(exerciseTypeMapper.mapToDto(testData.exerciseTypeEntity3)).thenReturn(testData.exerciseTypeResponseDto3);

        ExerciseTypeDto result = exerciseTypeService.createExercise(testData.exerciseTypeRequestDto3, "user1");

        assertNotNull(result);
        assertEquals(testData.exerciseTypeResponseDto3, result);
    }

    @Test
    void createExercise_UserNotFound() throws Exception {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                exerciseTypeService.createExercise(testData.exerciseTypeRequestDto3, "user3"));

        assertEquals("User with the username \"user3\" not found.", exception.getMessage());
    }

    @Test
    void createExercise_ExerciseAlreadyExists() throws Exception {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto1)).thenReturn(testData.exerciseTypeEntity1);
        when(exerciseTypeRepository.findByUserAndName(testData.user1, testData.exerciseTypeEntity1.getName()))
                .thenReturn(Optional.of(testData.exerciseTypeEntity1));

        ConflictException exception = assertThrows(ConflictException.class,
                () -> exerciseTypeService.createExercise(testData.exerciseTypeRequestDto1, "user1"));

        assertEquals("Exercise with the name 'exerciseType1' already exists.", exception.getMessage());


    }

    @Test
    void createExercise_CategoryNotFound() {
        testData.categoryRequestDto1.setId(UUID.randomUUID());
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryRequestDto1);
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(categoryRepository.findById(testData.categoryEntity1.getId())).thenReturn(Optional.empty());
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto1)).thenReturn(testData.exerciseTypeEntity1);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseTypeService.createExercise(testData.exerciseTypeRequestDto1, "user1"));

        assertEquals("Category with the ID " + testData.categoryRequestDto1.getId() + " not found.", exception.getMessage());
    }


//    @Test
//    void findAll() {
//    }

    @Test
    void findAllForUser_Success() {

    }

    @Test
    void findAllForUser_UserNotFound() {
    }

    @Test
    void deleteById_Success() {
    }

    @Test
    void deleteById_IdNotFound() {
    }

//    @Test
//    void deleteAll() {
//    }

    @Test
    void updateById() {
    }
}