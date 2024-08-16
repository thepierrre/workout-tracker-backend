package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryMapperTest {

    @Autowired
    CategoryMapper categoryMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        CategoryDto result = categoryMapper.mapToDto(testData.categoryEntity2);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getName(), "category2");
        assertEquals(result.getExerciseTypes(), List.of());
    }

    @Test
    void mapFromDto() {

        CategoryEntity result = categoryMapper.mapFromDto(testData.categoryRequestDto1);
        assertNotNull(result);
        assertEquals(result.getName(), "category1");
        assertNull(result.getExerciseTypes());
    }
}