package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoutineMapperTest {

    @Autowired
    RoutineMapper routineMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        testData.routineEntity1.setUser(testData.user1);
        testData.routineEntity1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        testData.routineEntity1.getExerciseTypes().add(testData.exerciseTypeEntity2);

        RoutineDto result = routineMapper.mapToDto(testData.routineEntity1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getUser().getUsername(), "user1");
        assertEquals(result.getExerciseTypes().getFirst().getName(), "exerciseType1");
        assertEquals(result.getExerciseTypes().getLast().getName(), "exerciseType2");
    }

    @Test
    void mapFromDto() {
        testData.routineRequestDto1.setUser(testData.userDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeRequestDto1);
        testData.routineRequestDto1.getExerciseTypes().add(testData.exerciseTypeRequestDto2);

        RoutineEntity result = routineMapper.mapFromDto(testData.routineRequestDto1);

        assertNotNull(result);
        assertEquals(result.getUser().getUsername(), "user1");
        assertEquals(result.getExerciseTypes().getFirst().getName(), "exerciseType1");
        assertEquals(result.getExerciseTypes().getLast().getName(), "exerciseType2");
    }
}