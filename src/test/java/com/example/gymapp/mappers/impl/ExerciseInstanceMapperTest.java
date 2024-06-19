package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExerciseInstanceMapperTest {

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        testData.exerciseInstanceEntity1.setWorkout(testData.workoutEntity1);
        ExerciseInstanceDto result = exerciseInstanceMapper.mapToDto(testData.exerciseInstanceEntity1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getWorkout().getRoutineName(), "routine1");
        assertEquals(result.getExerciseTypeName(), "exerciseType1");
      }

    @Test
    void mapFromDto() {
        testData.exerciseInstanceRequestDto2.setWorkout(testData.workoutRequestDto2);
        ExerciseInstanceEntity result = exerciseInstanceMapper.mapFromDto(testData.exerciseInstanceRequestDto2);

        assertNotNull(result);
        assertEquals(result.getWorkout().getRoutineName(), "routine2");
        assertEquals(result.getExerciseTypeName(), "exerciseType2");
      }
}