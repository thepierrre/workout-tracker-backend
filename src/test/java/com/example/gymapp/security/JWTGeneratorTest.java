package com.example.gymapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import javax.crypto.SecretKey;

import java.security.SecureRandom;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JWTGeneratorTest {

    private JWTGenerator jwtGenerator;
    private SecretKey key;

    @MockBean
    Authentication authentication;

    private final String username = "testUser";

    @BeforeEach
    void setUp() {
        jwtGenerator = new JWTGenerator();
        key = JWTGenerator.key;
    }

    @Test
    void generateToken() {
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromJWT() {
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);
        String extractedUsername = jwtGenerator.getUsernameFromJWT(token);

        assertEquals(extractedUsername, username);
    }

    @Test
    void validateToken_Success() {
        when(authentication.getName()).thenReturn(username);

        String token = jwtGenerator.generateToken(authentication);
        assertTrue(jwtGenerator.validateToken(token));
    }

    @Test
    void validateToken_ExpiredToken() {
        when(authentication.getName()).thenReturn(username);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis() - 2000))
                .expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(key)
                .compact();

        AuthenticationCredentialsNotFoundException exception = assertThrows(
                AuthenticationCredentialsNotFoundException.class, () -> {
                    jwtGenerator.validateToken(token);
                });

        assertEquals("JWT is expired.", exception.getMessage());
    }

    @Test
    void validateToken_MalformedToken() {
        when(authentication.getName()).thenReturn(username);

        String token = "malformedToken";

        AuthenticationCredentialsNotFoundException exception = assertThrows(
                AuthenticationCredentialsNotFoundException.class, () -> {
                    jwtGenerator.validateToken(token);
                });

        assertEquals("JWT is incorrect.", exception.getMessage());
    }

    @Test
    void validateToken_InvalidSignature() {
        when(authentication.getName()).thenReturn(username);
        
//        SecretKey anotherKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        byte[] keyBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);

        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 5000))
                .signWith(key)
                .compact();

        AuthenticationCredentialsNotFoundException exception = assertThrows(
                AuthenticationCredentialsNotFoundException.class, () -> {
                    jwtGenerator.validateToken(token);
                });

        assertEquals("JWT is incorrect.", exception.getMessage());
    }

    @Test
    void validateToken_IllegalArgument() {
        when(authentication.getName()).thenReturn(username);

        String token = null;

        AuthenticationCredentialsNotFoundException exception = assertThrows(
                AuthenticationCredentialsNotFoundException.class, () -> {
                    jwtGenerator.validateToken(token);
                });

        assertEquals("JWT is incorrect.", exception.getMessage());
    }
}