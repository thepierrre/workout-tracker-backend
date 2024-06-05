package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

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

    private UserEntity user;
    private ExerciseTypeDto exerciseTypeDto;
    private ExerciseTypeEntity exerciseTypeEntity;
    private CategoryEntity categoryEntity;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("user1")
                .email("username1@example.com")
                .password("pass1")
                .exerciseTypes(new ArrayList<>())
                .build();

        categoryDto = CategoryDto.builder()
                .id(UUID.fromString("5a8f0e74-3c6b-4c97-8f70-b0742cb1c3ec"))
                .name("category1")
                .build();

        categoryEntity = CategoryEntity.builder()
                .id(UUID.fromString("5a8f0e74-3c6b-4c97-8f70-b0742cb1c3ec"))
                .name("category1")
                .build();

        exerciseTypeDto = ExerciseTypeDto.builder()
                .name("exercise1")
                .categories(List.of())
                .build();

        exerciseTypeEntity = ExerciseTypeEntity.builder()
                .id(UUID.fromString("6b8f0e74-3c6b-4c97-8f70-b0742cb1c3ec"))
                .name("exercise")
                .user(user)
                .categories(List.of())
                .build();

    }

    @Test
    void testCreateExercise_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(exerciseTypeMapper.mapFromDto(exerciseTypeDto)).thenReturn(exerciseTypeEntity);
        when(exerciseTypeRepository.save(exerciseTypeEntity)).thenReturn(exerciseTypeEntity);
        when(exerciseTypeMapper.mapToDto(exerciseTypeEntity)).thenReturn(exerciseTypeDto);

        ExerciseTypeDto result = exerciseTypeService.createExercise(exerciseTypeDto, "user1");
        assertNotNull(result);
        assertEquals("exercise1", result.getName());

    }

    @Test
    void testCreateExercise_UserNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                exerciseTypeService.createExercise(exerciseTypeDto, "user1"));
    }


    @Test
    void testCreateExercise_ExerciseAlreadyExists() {

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("user1")
                .email("username1@example.com")
                .password("pass1")
                .exerciseTypes(new ArrayList<>())
                .build();

        ExerciseTypeEntity existingExercise = ExerciseTypeEntity.builder()
                .id(UUID.randomUUID())
                .name("exercise1")
                .user(user)
                .categories(List.of())
                .build();

        user.getExerciseTypes().add(existingExercise);

        exerciseTypeDto = ExerciseTypeDto.builder()
                .name("exercise1")
                .categories(List.of())
                .build();

        // Mock repository interactions
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        when(exerciseTypeMapper.mapFromDto(exerciseTypeDto)).thenReturn(ExerciseTypeEntity.builder()
                .name("exercise1")
                .categories(List.of())
                .build());

        // Act & Assert: attempt to create the exercise and expect a ConflictException
        ConflictException exception = assertThrows(ConflictException.class, () ->
                exerciseTypeService.createExercise(exerciseTypeDto, "user1")
        );

        // Verify that the exception message is as expected
        assertEquals("Exercise with the name 'exercise1' already exists.", exception.getMessage());
    }

    @Test
    void testCreateExercise_InternalServerError() {
    }


    @Test
    void testFindAll() {
        when(exerciseTypeRepository.findAll()).thenReturn((List.of(exerciseTypeEntity, exerciseTypeEntity)));

        List<ExerciseTypeDto> result = exerciseTypeService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllForUser() {
        exerciseTypeRepository.save(exerciseTypeEntity);
        user.setExerciseTypes(List.of(exerciseTypeEntity));
        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getExerciseTypes().stream()
                .map(exerciseType -> exerciseTypeMapper.mapToDto(exerciseType)).toList().size(), 1);

    }

    @Test
    void testDeleteById() {
        exerciseTypeRepository.save(exerciseTypeEntity);

    }

    @Test
    void testDeleteAll() {

    }

    @Test
    void testUpdateById() {
    }
}