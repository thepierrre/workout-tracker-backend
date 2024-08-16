package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkoutServiceTest {

    @Autowired
    WorkoutService workoutService;

    @MockBean
    WorkoutRepository workoutRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoutineRepository routineRepository;

    @MockBean
    WorkoutMapper workoutMapper;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();

        UserEntity user1 = testData.user1;

        user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        user1.getExerciseTypes().add(testData.exerciseTypeEntity2);
        user1.getExerciseTypes().add(testData.exerciseTypeEntity3);

        testData.routineEntity1.getRoutineExercises().add(testData.routineExerciseEntity1);
        testData.routineEntity1.getRoutineExercises().add(testData.routineExerciseEntity2);
        testData.routineEntity2.getRoutineExercises().add(testData.routineExerciseEntity1);
        testData.routineEntity2.getRoutineExercises().add(testData.routineExerciseEntity3);
        testData.routineEntity3.getRoutineExercises().add(testData.routineExerciseEntity2);
        testData.routineEntity3.getRoutineExercises().add(testData.routineExerciseEntity3);

        user1.getRoutines().add(testData.routineEntity1);
        user1.getRoutines().add(testData.routineEntity2);
        user1.getRoutines().add(testData.routineEntity3);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        workoutRepository.deleteAll();
    }

    @Test
    void findAllForUser_Success() {
        when(workoutRepository.findByUserUsername("user1")).thenReturn(Optional.of(List.of(testData.workoutEntity1, testData.workoutEntity2)));
        when(workoutMapper.mapToDto(testData.workoutEntity1)).thenReturn(testData.workoutResponseDto1);
        when(workoutMapper.mapToDto(testData.workoutEntity2)).thenReturn(testData.workoutResponseDto2);

        List<WorkoutDto> result = workoutService.findAllForUser("user1");

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(testData.workoutResponseDto1, result.get(0));
        assertEquals(testData.workoutResponseDto2, result.get(result.size() - 1));

    }

    @Test
    void findAllForUser_UserNotFound() throws Exception {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                workoutService.findAllForUser("user1"));

        assertEquals("User with the username \"user1\" not found.", exception.getMessage());
    }

    @Test
    void findById_Success() {
        UUID workoutId = testData.workoutEntity1.getId();

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(testData.workoutEntity1));
        when(workoutMapper.mapToDto(testData.workoutEntity1)).thenReturn(testData.workoutResponseDto1);
        Optional<WorkoutDto> result = workoutService.findById(workoutId);

        assertNotNull(result);
    }

    @Test
    void findById_WorkoutNotFound() throws Exception {
        UUID workoutId = UUID.randomUUID();

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> workoutService.findById(workoutId));

        assertEquals(String.format("Workout with the ID \"%s\" not found.", workoutId), exception.getMessage());

    }

    @Test
    void createWorkout_Success() {
        testData.workoutResponseDto1.setUser(testData.userDto1);
        testData.workoutResponseDto1.setExerciseInstances(List.of(testData.exerciseInstanceResponseDto1));

        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));
        when(routineRepository.findByUserAndName(testData.user1, testData.workoutRequestDto1.getRoutineName()))
                .thenReturn(Optional.ofNullable(testData.routineEntity1));
        when(workoutMapper.mapFromDto(testData.workoutRequestDto1)).thenReturn(testData.workoutEntity1);
        when(workoutRepository.save(testData.workoutEntity1)).thenReturn(testData.workoutEntity1);
        when(workoutMapper.mapToDto(testData.workoutEntity1)).thenReturn(testData.workoutResponseDto1);

        WorkoutDto result = workoutService.createWorkout(testData.workoutRequestDto1, "user1");

        assertNotNull(result.getId());
        assertEquals(result.getRoutineName(), "routine1");
        assertEquals(result.getCreationDate(), LocalDate.of(2024, 4, 30));
        assertEquals(result.getUser(), testData.userDto1);
        assertEquals(result.getExerciseInstances(), List.of(testData.exerciseInstanceResponseDto1));

    }

    @Test
    void createWorkout_UserNotFound() throws Exception {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                workoutService.createWorkout(testData.workoutRequestDto1, "user3"));


        assertEquals("User with the username \"user3\" not found.", exception.getMessage());

    }

    @Test
    void createWorkout_RoutineNotFound() throws Exception {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.ofNullable(testData.user1));

        when(routineRepository.findByUserAndName(testData.user1, testData.workoutRequestDto4.getRoutineName()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> workoutService.createWorkout(testData.workoutRequestDto4, "user1"));

        assertEquals("Routine with the name \"routine4\" not found for user \"user1\".", exception.getMessage());

    }

    @Test
    void deleteById_Success() {
        UUID workoutId = testData.workoutEntity1.getId();
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(testData.workoutEntity1));

        workoutService.deleteById(workoutId);

        verify(workoutRepository, times(1)).deleteById(workoutId);
    }

    @Test
    void deleteById_WorkoutNotFound() {
        UUID workoutId = UUID.randomUUID();
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                workoutService.deleteById(workoutId));
    }
}