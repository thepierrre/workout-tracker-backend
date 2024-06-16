package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        String loginResponse = authService.login(loginDto, response);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String logoutResponse = authService.logout(request, response);
        return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        String registerResponse = authService.register(registerDto);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }
}
