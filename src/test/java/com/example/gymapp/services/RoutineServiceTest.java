package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.helpers.UserDataHelper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
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

//    private UserEntity user;
//    private RoutineDto routineDto1;
//    private RoutineDto routineDto2;
//    private RoutineEntity routineEntity1;
//    private RoutineEntity routineEntity2;
//    private ExerciseTypeDto exerciseTypeDto1;
//    private ExerciseTypeDto exerciseTypeDto2;
//    private ExerciseTypeEntity exerciseTypeEntity1;
//    private ExerciseTypeEntity exerciseTypeEntity2;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {

        testData = TestDataInitializer.initializeTestData();

//        user = UserDataHelper.createUserEntity(
//                "user1",
//                "user1@example.com",
//                "pass1",
//                exercises,
//                routines,
//                workouts);
//
//        exerciseTypeDto1 = ExerciseTypeDto.builder()
//                .name("exercise1")
//                .categories(List.of())
//                .build();
//
//        exerciseTypeDto2 = ExerciseTypeDto.builder()
//                .name("exercise2")
//                .categories(List.of())
//                .build();
//
//        exerciseTypeEntity1 = ExerciseTypeEntity.builder()
//                .id(UUID.randomUUID())
//                .name("exercise1")
//                .user(user)
//                .categories(List.of())
//                .build();

//        exerciseTypeEntity2 = ExerciseTypeEntity.builder()
//                .name("exercise2")
//                .categories(List.of())
//                .build();

        testData.user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        testData.user1.getExerciseTypes().add(testData.exerciseTypeEntity2);

//        routineDto1 = RoutineDto.builder()
//                .id(UUID.randomUUID())
//                .name("routine1")
//                .build();
//
//        routineDto2 = RoutineDto.builder()
//                .id(UUID.randomUUID())
//                .name("routine2")
//                .build();
//
//        routineEntity1 = RoutineEntity.builder()
//                .id(UUID.randomUUID())
//                .name("routine1")
//                .build();
//
//        routineEntity2 = RoutineEntity.builder()
//                .id(UUID.randomUUID())
//                .name("routine2")
//                .build();

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
    void testUpdateById() {
    }

    @Test
    void testDeleteById() {
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
    void testDeleteAll() {
    }
}