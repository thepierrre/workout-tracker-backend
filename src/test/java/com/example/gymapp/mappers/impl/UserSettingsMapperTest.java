package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserSettingsMapperTest {

    @Autowired
    UserSettingsMapper userSettingsMapperMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void mapToDto() {
        UserSettingsDto result = userSettingsMapperMapper.mapToDto(testData.userSettingsEntity1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getChangeThreshold(), 1.0);
        assertEquals(result.getWeightUnit(), "kgs");
    }

    @Test
    void mapFromDto() {
        UserSettingsEntity result = userSettingsMapperMapper.mapFromDto(testData.userSettingsRequestDto2);

        assertNotNull(result);
        assertEquals(result.getChangeThreshold(), 10.0);
        assertEquals(result.getWeightUnit(), "kgs");
    }
}