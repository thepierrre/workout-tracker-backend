package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/users")
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/users")
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto updatedUser = userService.update(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/training-routines")
    public ResponseEntity<List<TrainingRoutineDto>> getTrainingRoutinesForUser(@PathVariable("userId") String userId) {
        UUID id;
        try {
            id = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Provided user ID cannot be converted to a valid UUID");
        }

        List<TrainingRoutineDto> foundTrainingRoutines;
        try {
            foundTrainingRoutines = new ArrayList<>(userService.getTrainingRoutinesForUser(id));
        } catch (ResponseStatusException e) {
            throw e;
        }
        return ResponseEntity.ok(foundTrainingRoutines);
    }

    @GetMapping(path = "/users{userId}/workouts")
    public ResponseEntity<List<WorkoutDto>> getWorkoutsForUser(@PathVariable("userId") String userId) {
        UUID id;
        try {
            id = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Provided user ID cannot be converted to a valid UUID");
        }

        List<WorkoutDto> foundWorkouts;
       try {
           foundWorkouts = new ArrayList<>(userService.getWorkoutsForUser(id));
       } catch (ResponseStatusException e) {
           throw e;
       }
        return ResponseEntity.ok(foundWorkouts);
    }

    @GetMapping(path = "/users/{userId}/exercise-types")
    public ResponseEntity<List<ExerciseTypeDto>> getExerciseTypesForUser(@PathVariable("userId") String userId) throws Exception {
        UUID id;
        try {
            id = UUID.fromString(userId);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Provided user ID cannot be converted to a valid UUID");

        }

        List<ExerciseTypeDto> foundExerciseTypes;
        try {
            foundExerciseTypes = new ArrayList<>(userService.getExerciseTypesForUser(id));
        } catch (ResponseStatusException e) {
            throw e;
        }
        return ResponseEntity.ok(foundExerciseTypes);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> BadRequestHandler(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> NotFoundHandler(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(Map.of("message", "User with the provided ID was not found"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}




