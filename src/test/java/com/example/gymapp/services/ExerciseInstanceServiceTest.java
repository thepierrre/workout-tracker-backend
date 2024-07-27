package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkingSetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExerciseInstanceServiceTest {

    @Autowired
    ExerciseInstanceService exerciseInstanceService;

    @MockBean
    ExerciseInstanceRepository exerciseInstanceRepository;

    @MockBean
    WorkingSetRepository workingSetRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExerciseInstanceMapper exerciseInstanceMapper;

    @MockBean
    WorkingSetMapper workingSetMapper;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void createWorkingSetforExercise_Success() {
        testData.exerciseInstanceEntity1.setWorkingSets(new ArrayList<>(List.of(testData.instanceWorkingSetEntity1)));
        testData.workoutEntity1.setExerciseInstances(new ArrayList<>(List.of(testData.exerciseInstanceEntity1)));

        testData.exerciseInstanceResponseDto1.setWorkingSets(
                new ArrayList<>(List.of(testData.workingSetResponseDto1, testData.workingSetResponseDto2)));

        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(testData.user1));
        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));
        when(workingSetRepository.save(testData.instanceWorkingSetEntity1))
                .thenReturn(testData.instanceWorkingSetEntity1);
        when(workingSetMapper.mapFromDto(testData.workingSetRequestDto1))
                .thenReturn(testData.instanceWorkingSetEntity1);
        when(exerciseInstanceMapper.mapToDto(testData.exerciseInstanceEntity1))
                .thenReturn(testData.exerciseInstanceResponseDto1);

        ExerciseInstanceDto result = exerciseInstanceService
                .createWorkingSetforExercise(testData.exerciseInstanceEntity1.getId(), testData.workingSetRequestDto1);

        assertNotNull(result);
        assertEquals(result.getWorkingSets().size(), 2);
        assertEquals(result.getWorkingSets().get(result.getWorkingSets().size() - 1).getReps(), (short) 9);
        assertEquals(result.getWorkingSets().get(result.getWorkingSets().size() - 1).getWeight(), (short) 40);
    }

    @Test
    void createWorkingSetforExercise_ExerciseIdNotFound() {
        UUID id = UUID.randomUUID();

        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.deleteById(id));
        assertEquals("Exercise instance with the ID " + id + " not found.", exception.getMessage());
    }

    @Test
    void updateWorkingSetById_Success() {
        UUID workingSetId = testData.workingSetResponseDto1.getId();
        testData.exerciseInstanceEntity1.setWorkingSets(new ArrayList<>(List.of(testData.instanceWorkingSetEntity1)));
        testData.workoutEntity1.setExerciseInstances(new ArrayList<>(List.of(testData.exerciseInstanceEntity1)));

        testData.exerciseInstanceResponseDto1.setWorkingSets(
                new ArrayList<>(List.of(testData.workingSetResponseDto1, testData.workingSetResponseDto2)));

        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(testData.user1));
        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));
        when(workingSetRepository.findById(workingSetId))
                .thenReturn(Optional.of(testData.instanceWorkingSetEntity1));
        when(workingSetRepository.save(testData.instanceWorkingSetEntity1))
                .thenReturn(testData.instanceWorkingSetEntity1);
        when(workingSetMapper.mapFromDto(testData.workingSetRequestDto1))
                .thenReturn(testData.instanceWorkingSetEntity1);
        when(exerciseInstanceMapper.mapToDto(testData.exerciseInstanceEntity1))
                .thenReturn(testData.exerciseInstanceResponseDto1);

        ExerciseInstanceDto result = exerciseInstanceService
                .updateWorkingSetById(
                        testData.exerciseInstanceEntity1.getId(),
                        workingSetId,
                        testData.workingSetRequestDto1);

        assertNotNull(result);
        assertEquals(result.getWorkingSets().size(), 2);
        assertEquals(result.getWorkingSets().get(result.getWorkingSets().size() - 1).getReps(), (short) 9);
        assertEquals(result.getWorkingSets().get(result.getWorkingSets().size() - 1).getWeight(), (short) 40);
    }

    @Test
    void updateWorkingSetById_ExerciseIdNotFound() {
        UUID exerciseId = UUID.randomUUID();
        UUID setId = testData.workingSetResponseDto3.getId();

        when(exerciseInstanceRepository.findById(exerciseId))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.updateWorkingSetById(exerciseId, setId, testData.workingSetRequestDto3));
        assertEquals("Exercise instance with the ID " + exerciseId + " not found.", exception.getMessage());
    }

    @Test
    void updateWorkingSetById_SetIdNotFound() {
        UUID exerciseId = testData.exerciseInstanceEntity1.getId();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));
        when(workingSetRepository.findById(setId))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.updateWorkingSetById(exerciseId, setId, testData.workingSetRequestDto3));
        assertEquals("Set with the ID " + setId + " not found.", exception.getMessage());
    }

    @Test
    void deleteWorkingSetById_Success() {
        UUID exerciseId = testData.exerciseInstanceEntity1.getId();
        UUID workingSetId = testData.instanceWorkingSetEntity1.getId();

        testData.exerciseInstanceEntity1.setWorkingSets(new ArrayList<>(List.of(testData.instanceWorkingSetEntity1)));

        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));
        when(workingSetRepository.findById(workingSetId))
                .thenReturn(Optional.of(testData.instanceWorkingSetEntity1));

        exerciseInstanceService.deleteWorkingSetById(exerciseId, workingSetId);
        verify(workingSetRepository, times(1)).deleteById(workingSetId);
    }

    @Test
    void deleteWorkingSetById_ExerciseIdNotFound() {
        UUID exerciseId = UUID.randomUUID();
        UUID setId = testData.workingSetResponseDto3.getId();

        when(exerciseInstanceRepository.findById(exerciseId))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.deleteWorkingSetById(exerciseId, setId));
        assertEquals("Exercise instance with the ID " + exerciseId + " not found.", exception.getMessage());
    }

    @Test
    void deleteWorkingSetById_SetIdNotFound() {
        UUID exerciseId = testData.exerciseInstanceEntity1.getId();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceRepository.findById(testData.exerciseInstanceEntity1.getId()))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));
        when(workingSetRepository.findById(setId))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.deleteWorkingSetById(exerciseId, setId));
        assertEquals("Set with the ID " + setId + " not found.", exception.getMessage());
    }

    @Test
    void deleteExerciseById_Success() {
        UUID exerciseId = testData.exerciseInstanceEntity1.getId();
        when(exerciseInstanceRepository.findById(exerciseId))
                .thenReturn(Optional.of(testData.exerciseInstanceEntity1));

        exerciseInstanceService.deleteById(exerciseId);
        verify(exerciseInstanceRepository, times(1)).deleteById(exerciseId);
    }

    @Test
    void deleteExerciseById_ExerciseIdNotFound() {
        UUID exerciseId = UUID.randomUUID();

        when(exerciseInstanceRepository.findById(exerciseId))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseInstanceService.deleteById(exerciseId));
        assertEquals("Exercise instance with the ID " + exerciseId + " not found.", exception.getMessage());
    }
}