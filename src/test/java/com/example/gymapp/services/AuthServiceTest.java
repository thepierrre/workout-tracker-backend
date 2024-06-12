package com.example.gymapp.services;

import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.security.JWTGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;
    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void login_Success() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findByUsername(testData.loginDto1.getUsername())).thenReturn(Optional.ofNullable(testData.user1));
        when(passwordEncoder.matches(testData.loginDto1.getPassword(), testData.user1.getPassword())).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn("token");

        ResponseEntity<String> result = authService.login(testData.loginDto1, response);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User \"user1\" logged in.", result.getBody());

        verify(response, times(1)).addCookie(any());
    }

    @Test
    void login_InvalidUsername() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findByUsername(testData.loginDto1.getUsername())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> authService.login(testData.loginDto1, response));

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    void login_InvalidPassword() {
        testData.loginDto1.setPassword("invalid");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findByUsername(testData.loginDto1.getUsername())).thenReturn(Optional.ofNullable(testData.user1));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.login(testData.loginDto1, response));

        assertEquals("Invalid username or password.", exception.getMessage());

    }

    // check if user is logged out (e.g. no cookie)
    @Test
    void logout_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SecurityContextLogoutHandler logoutHandler = mock(SecurityContextLogoutHandler.class);

        ResponseEntity<String> result = authService.logout(request, response);

        assertEquals("Logging out successful.", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

      }

    @Test
    void register_Success() {
        when(userRepository.existsByUsername(testData.registerDto1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(testData.registerDto1.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role("USER")));

        ResponseEntity<String> response = authService.register(testData.registerDto1);
        assertEquals("User \"user1\" registered.", response.getBody());
    }

    @Test
    void register_UsernameAlreadyExists() {
        when(userRepository.existsByUsername(testData.registerDto1.getUsername())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class,
                () -> authService.register(testData.registerDto1));

        assertEquals("User with the username \"user1\" already exists.", exception.getMessage());
    }

    @Test
    void register_EmailAlreadyExists() {
        when(userRepository.existsByEmail(testData.registerDto1.getEmail())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class,
                () -> authService.register(testData.registerDto1));

        assertEquals("User with the email \"user1@example.com\" already exists.", exception.getMessage());
    }

    @Test
    void register_UserRoleNotExisting() {
        when(userRepository.existsByUsername(testData.registerDto1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(testData.registerDto1.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.register(testData.registerDto1));

        assertEquals("Cannot register a new user because the \"USER\" role hasn't been set.", exception.getMessage());
    }
}