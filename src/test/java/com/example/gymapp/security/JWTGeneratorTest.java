package com.example.gymapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JWTGeneratorTest {

    private JWTGenerator jwtGenerator;

    @Mock
    Authentication authentication;

    @BeforeEach
    void setUp() {
        jwtGenerator = new JWTGenerator();
    }

    @Test
    void generateToken() {
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromJWT() {
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);

        String extractedUsername = jwtGenerator.getUsernameFromJWT(token);

        assertEquals(extractedUsername, username);
    }

    @Test
    void validateToken_Success() {
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);
        assertTrue(jwtGenerator.validateToken(token));
    }
}