package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.ExerciseTypeDataHelper;
import com.example.gymapp.helpers.RoutineDataHelper;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.helpers.UserDataHelper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoutineServiceTest {

    @Autowired
    RoutineService routineService;

    @MockBean
    RoutineRepository routineRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExerciseTypeRepository exerciseTypeRepository;

    @MockBean
    RoutineMapper routineMapper;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {

        testData = TestDataInitializer.initializeTestData();

        testData.user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        testData.user1.getExerciseTypes().add(testData.exerciseTypeEntity2);

        testData.user1.getRoutines().add(testData.routineEntity1);
    }

    @AfterEach
    void tearDown() {
        exerciseTypeRepository.deleteAll();
        routineRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateRoutine_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(routineMapper.mapFromDto(testData.routineRequestDto2)).thenReturn(testData.routineEntity2);
        when(routineRepository.save(testData.routineEntity2)).thenReturn(testData.routineEntity2);
        when(routineMapper.mapToDto(testData.routineEntity2)).thenReturn(testData.routineResponseDto2);

        RoutineDto result = routineService.createRoutine(testData.routineRequestDto2, "user1");

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("routine2", result.getName());
    }

    @Test
    void testCreateRoutine_RoutineAlreadyExists() {
        testData.user1.getRoutines().add(testData.routineEntity2);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(routineMapper.mapFromDto(testData.routineRequestDto2)).thenReturn(testData.routineEntity2);

        ConflictException exception = assertThrows(ConflictException.class, () ->
                routineService.createRoutine(testData.routineRequestDto2, "user1"));

        assertEquals("Routine with the name 'routine2' already exists.", exception.getMessage());

    }

    @Test
    void testFindAll() {
        when(routineRepository.findAll()).thenReturn((List.of(testData.routineEntity1, testData.routineEntity2)));

        List<RoutineDto> result = routineService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllForUser() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        List<RoutineDto> result = routineService.findAllForUser("user1");
        assertEquals(result.size(), 1);
    }

    @Test
    void updateById_Success() {

        UUID id = testData.routineEntity1.getId();

        RoutineEntity editedEntity = RoutineDataHelper.createRoutineEntity("edited");
        editedEntity.setExerciseTypes(List.of(testData.exerciseTypeEntity2, testData.exerciseTypeEntity3));
        editedEntity.setId(id);

        RoutineDto editedResponseDto = RoutineDataHelper.createRoutineResponseDto("edited");
        editedResponseDto.setExerciseTypes(List.of(testData.exerciseTypeResponseDto2, testData.exerciseTypeResponseDto3));
        editedResponseDto.setId(id);

        RoutineDto editedRequestDto = RoutineDataHelper.createRoutineRequestDto("edited");
        editedRequestDto.setExerciseTypes(List.of(testData.exerciseTypeRequestDto2, testData.exerciseTypeRequestDto3));
        editedRequestDto.setId(id);

        when(routineRepository.findById(id)).thenReturn(Optional.ofNullable(testData.routineEntity1));
        when(routineMapper.mapFromDto(editedRequestDto)).thenReturn(editedEntity);
        when(routineMapper.mapToDto(editedEntity)).thenReturn(editedResponseDto);
        when(routineRepository.save(any(RoutineEntity.class))).thenReturn(editedEntity);


        RoutineDto result = routineService.updateById(id, editedRequestDto);

        assertNotNull(result);
        assertEquals(testData.routineEntity1.getId(), editedResponseDto.getId());
        assertEquals(editedResponseDto.getName(), "edited");
        assertEquals(editedResponseDto.getExerciseTypes(), List.of(testData.exerciseTypeResponseDto2, testData.exerciseTypeResponseDto3));
    }

    @Test
    void testDeleteById_Success() {
        UUID id = testData.routineEntity1.getId();
        when(routineRepository.findById(id)).thenReturn(Optional.ofNullable(testData.routineEntity1));

        doAnswer(invocation -> {
            testData.user1.getRoutines().remove(testData.routineEntity1);
            return null;
        }).when(routineRepository).deleteById(id);

        routineService.deleteById(id);
        verify(routineRepository, times(1)).deleteById(id);
        assertTrue(testData.user1.getRoutines().isEmpty());

    }

    @Test
    void deleteById_IdNotFound() {
        UUID id = UUID.randomUUID();
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> routineService.deleteById(id));

        assertEquals("Routine with the ID " + id + " not found.", exception.getMessage());
    }

    @Test
    void updateById_IdNotFound() {
        UUID id = UUID.randomUUID();
        RoutineDto editedRequestDto = RoutineDataHelper.createRoutineRequestDto("edited");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> routineService.updateById(id, editedRequestDto));

        assertEquals("Routine with the ID " + id + " not found.", exception.getMessage());
    }

    @Test
    void testDeleteAll() {
    }
}