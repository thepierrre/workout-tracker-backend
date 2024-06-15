package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.*;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoutineMapper routineMapper;

    @Autowired
    WorkoutMapper workoutMapper;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {

        List<UserDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") UUID id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserDto foundUser = userMapper.mapToDto(user.get());
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserEntity> user = userService.findByUsername(userDetails.getUsername());
        if (user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(user.get().getUsername());
            userResponseDto.setEmail(user.get().getEmail());
            userResponseDto.setRoutines(user.get().getRoutines().stream()
                    .map(routineMapper::mapToDto).toList());
            userResponseDto.setWorkouts(user.get().getWorkouts().stream()
                    .map(workoutMapper::mapToDto).toList());
            userResponseDto.setExerciseTypes(user.get().getExerciseTypes().stream()
                    .map(exerciseTypeMapper::mapToDto).toList());
            return ResponseEntity.ok(userResponseDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") UUID id) {
        try {
            userService.deleteById(id);
        } catch (ResponseStatusException e) {
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}




