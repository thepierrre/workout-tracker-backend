package com.example.gymapp.services;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.security.JWTGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<String> login(LoginDto loginDto, HttpServletResponse response) {
        try {

            UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new AuthenticationException(
                            "Invalid username or password.") {
                    });

            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // Use true if HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);

            response.addCookie(cookie);

            return new ResponseEntity<>("User \"" + loginDto.getUsername() + "\" logged in.", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Logging in failed due to an unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return new ResponseEntity<>("Logging out successful.", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Logging out failed due to an unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("User with the username \"" + registerDto.getUsername() + "\" already exists.", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("User with the e-mail \"" + registerDto.getEmail() + "\" already exists.", HttpStatus.CONFLICT);
        }

        try {
            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            Role role = roleRepository.findByName("USER")
                            .orElseThrow(
                                    () -> new EntityNotFoundException(
                                            "Cannot register a new user because the \"USER\" role hasn't been set."));
            user.setRoles(Collections.singletonList(role));

            userRepository.save(user);

            return new ResponseEntity<>("User \"" + registerDto.getUsername() + "\" registered.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed due to an unexpected error.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

