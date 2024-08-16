package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryMapper categoryMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void findAll_Success() {
        when(categoryRepository.findAll()).thenReturn(List.of(testData.categoryEntity1, testData.categoryEntity2));
        when(categoryMapper.mapToDto(testData.categoryEntity1)).thenReturn(testData.categoryResponseDto1);
        when(categoryMapper.mapToDto(testData.categoryEntity2)).thenReturn(testData.categoryResponseDto2);

        List<CategoryDto> result = categoryService.findAll();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "category1");
        assertEquals(result.get(1).getName(), "category2");
    }
}