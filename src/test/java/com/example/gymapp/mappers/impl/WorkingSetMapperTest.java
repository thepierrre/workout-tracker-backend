package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WorkingSetMapperTest {

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }
    @Autowired
    WorkingSetMapper workingSetMapper;

    @Test
    void mapToDto() {
        InstanceWorkingSetDto result = workingSetMapper.mapToDto(testData.instanceWorkingSetEntity1);

        assertNotNull(result);
        assertEquals(result.getReps(), (short) 10);
        assertEquals(result.getWeight(), (short) 50);
      }

    @Test
    void mapFromDto() {
        InstanceWorkingSetEntity result = workingSetMapper.mapFromDto(testData.workingSetResponseDto1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getReps(), (short) 10);
        assertEquals(result.getWeight(), (short) 50);
      }
}