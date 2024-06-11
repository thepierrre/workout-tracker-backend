package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }


    @Test
    void mapToDto() {
        UserDto result = userMapper.mapToDto(testData.user1);

        assertNotNull(result);
        assertEquals(result.getUsername(), "user1");
        assertEquals(result.getEmail(), "user1@example.com");
        assertEquals(result.getPassword(), "encoded1");
      }

    @Test
    void mapFromDto() {
        UserEntity result = userMapper.mapFromDto(testData.userDto1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getUsername(), "user1");
        assertEquals(result.getEmail(), "user1@example.com");
        assertEquals(result.getPassword(), "pass1");
      }
}