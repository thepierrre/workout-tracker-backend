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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

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
    ExerciseTypeMapper exerciseTypeMapper;

    private UserEntity user;
    private ExerciseTypeDto exerciseTypeDto1;

    private ExerciseTypeDto exerciseTypeDto2;
    private ExerciseTypeEntity exerciseTypeEntity1;
    private ExerciseTypeEntity exerciseTypeEntity2;


    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("user1")
                .email("username1@example.com")
                .password("pass1")
                .exerciseTypes(new ArrayList<>())
                .build();

        exerciseTypeDto1 = ExerciseTypeDto.builder()
                .name("exercise1")
                .categories(List.of())
                .build();

        exerciseTypeDto2 = ExerciseTypeDto.builder()
                .name("exercise2")
                .categories(List.of())
                .build();

        exerciseTypeEntity1 = ExerciseTypeEntity.builder()
                .id(UUID.randomUUID())
                .name("exercise1")
                .user(user)
                .categories(List.of())
                .build();

        user.getExerciseTypes().add(exerciseTypeEntity1);

        exerciseTypeEntity2 = ExerciseTypeEntity.builder()
                .name("exercise2")
                .categories(List.of())
                .build();

    }

    @AfterEach
    void tearDown() {
        exerciseTypeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateExercise_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(exerciseTypeMapper.mapFromDto(exerciseTypeDto2)).thenReturn(exerciseTypeEntity2);
        when(exerciseTypeRepository.save(exerciseTypeEntity2)).thenReturn(exerciseTypeEntity2);
        when(exerciseTypeMapper.mapToDto(exerciseTypeEntity2)).thenReturn(exerciseTypeDto2);

        ExerciseTypeDto result = exerciseTypeService.createExercise(exerciseTypeDto2, "user1");
        assertNotNull(result);
        assertEquals("exercise2", result.getName());
    }


    @Test
    void testCreateExercise_UserNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                exerciseTypeService.createExercise(exerciseTypeDto1, "user1"));
    }


    @Test
    void testCreateExercise_ExerciseAlreadyExists() {
        user.getExerciseTypes().add(exerciseTypeEntity2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(exerciseTypeMapper.mapFromDto(exerciseTypeDto1)).thenReturn(ExerciseTypeEntity.builder()
                .name("exercise1")
                .categories(List.of())
                .build());

        ConflictException exception = assertThrows(ConflictException.class, () ->
                exerciseTypeService.createExercise(exerciseTypeDto1, "user1")
        );

        assertEquals("Exercise with the name 'exercise1' already exists.", exception.getMessage());
    }

    @Test
    void testCreateExercise_InternalServerError() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(exerciseTypeMapper.mapFromDto(exerciseTypeDto2)).thenReturn(exerciseTypeEntity2);
        when(exerciseTypeRepository.save(exerciseTypeEntity2)).thenThrow(new RuntimeException());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                exerciseTypeService.createExercise(exerciseTypeDto2, "user1"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Couldn't create a new exercise due to an unexpected error.", exception.getReason());

    }


    @Test
    void testFindAll() {
        when(exerciseTypeRepository.findAll()).thenReturn((List.of(exerciseTypeEntity2, exerciseTypeEntity2)));

        List<ExerciseTypeDto> result = exerciseTypeService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllForUser() {
        exerciseTypeRepository.save(exerciseTypeEntity2);
        user.setExerciseTypes(List.of(exerciseTypeEntity2));
        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getExerciseTypes().stream()
                .map(exerciseType -> exerciseTypeMapper.mapToDto(exerciseType)).toList().size(), 1);

    }

    @Test
    void testDeleteById() {
        UUID id = exerciseTypeEntity1.getId();
        exerciseTypeEntity1.setRoutines(new ArrayList<>()); // Initialize routines as empty list
        exerciseTypeEntity1.setCategories(new ArrayList<>()); // Initialize categories as empty list
        when(exerciseTypeRepository.findById(id)).thenReturn(Optional.of(exerciseTypeEntity1));

        exerciseTypeService.deleteById(id);

        assertTrue(user.getExerciseTypes().isEmpty());

    }

    @Test
    void testDeleteAll() {
        exerciseTypeService.deleteAll();
        assertEquals(exerciseTypeService.findAll().size(), 0);
    }

    @Test
    void testUpdateById() {
    }
}