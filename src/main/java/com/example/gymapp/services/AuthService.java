package com.example.gymapp.services;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.UserSettingsRepository;
import com.example.gymapp.security.JWTGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private Optional<String> getUsernameFromLogin(LoginDto loginDto) {
        return Optional.ofNullable(loginDto.getUsername());
    }

    private Optional<String> getEmailFromLogin(LoginDto loginDto) {
        return Optional.ofNullable(loginDto.getEmail());
    }

    public String login(LoginDto loginDto, HttpServletResponse response) {

        UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Use true if HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(43200); // 12 h

        response.addCookie(cookie);

        return "User \"" + loginDto.getUsername() + "\" logged in.";
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ConflictException("User with the username \"" + registerDto.getUsername() + "\" already exists.");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ConflictException("User with the email \"" + registerDto.getEmail() + "\" already exists.");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Cannot register a new user because the \"USER\" role hasn't been set."));

        UserEntity user = new UserEntity(
                registerDto.getUsername(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                new ArrayList<>(List.of(role))

        );

        UserSettingsEntity userSettingsEntity = new UserSettingsEntity(
                (double) 1,
                "kgs"
                //user
        );

        user.setUserSettings(userSettingsEntity);
        userRepository.save(user);

        return "User \"" + registerDto.getUsername() + "\" registered.";
    }

    public void createUserIfNotExistent(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isEmpty()) {
            register(registerDto);
        }
    }
}

