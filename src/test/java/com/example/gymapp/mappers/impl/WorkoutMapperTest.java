package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WorkoutMapperTest {

    @Autowired
    WorkoutMapper workoutMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        testData.workoutEntity1.setUser(testData.user1);
        testData.workoutEntity1.getExerciseInstances().add(testData.exerciseInstanceEntity1);
        testData.workoutEntity1.getExerciseInstances().add(testData.exerciseInstanceEntity2);
        WorkoutDto result = workoutMapper.mapToDto(testData.workoutEntity1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getUser().getUsername(), "user1");
        assertEquals(result.getRoutineName(), "routine1");
        assertEquals(result.getCreationDate(), LocalDate.of(2024, 4, 30));
        assertEquals(result.getExerciseInstances().getFirst().getExerciseTypeName(), "exerciseType1");
        assertEquals(result.getExerciseInstances().getLast().getExerciseTypeName(), "exerciseType2");
    }

    @Test
    void mapFromDto() {
        WorkoutEntity result = workoutMapper.mapFromDto(testData.workoutRequestDto1);

        assertNotNull(result);
        assertEquals(result.getRoutineName(), "routine1");
        assertEquals(result.getCreationDate(), LocalDate.of(2024, 4, 30));
    }
}