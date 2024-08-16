package com.example.gymapp.services;

import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void findByUsername_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        Optional<UserEntity> result = userService.findByUsername("user1");
        assertEquals(result, Optional.of(testData.user1));
    }

    @Test
    void findByUsername_UserNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.findByUsername("user1"));
        assertEquals("User with the username \"user1\" not found.", exception.getMessage());
    }
}