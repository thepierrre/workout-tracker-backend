package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExerciseTypeMapperTest {

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        testData.exerciseTypeEntity1.setUser(testData.user2);
        ExerciseTypeDto result = exerciseTypeMapper.mapToDto(testData.exerciseTypeEntity1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getName(), "exerciseType1");
        assertEquals(result.getUser().getUsername(), "user2");
        assertEquals(result.getCategories(), List.of());
    }

    @Test
    void mapFromDto() {
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryRequestDto1);
        ExerciseTypeEntity result = exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto1);

        assertNotNull(result);
        assertEquals(result.getName(), "exerciseType1");
        assertEquals(result.getCategories().getFirst().getName(), "category1");
    }
}