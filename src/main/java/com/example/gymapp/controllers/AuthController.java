package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.security.JWTGenerator;
import com.example.gymapp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymapp.domain.entities.Role;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        return authService.login(loginDto, response);
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> logoutResponse = authService.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);

    }



}
