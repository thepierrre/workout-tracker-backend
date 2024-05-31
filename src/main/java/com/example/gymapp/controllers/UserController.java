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

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<UserDto> update(@PathVariable("userId") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto updatedUser = userService.update(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(path = "{userId}/routines")
    public ResponseEntity<List<RoutineDto>> getTrainingRoutinesForUser(@PathVariable("userId") String userId) {
        UUID id;
        try {
            id = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Provided user ID cannot be converted to a valid UUID");
        }

        List<RoutineDto> foundTrainingRoutines;
        foundTrainingRoutines = new ArrayList<>(userService.getTrainingRoutinesForUser(id));

        return ResponseEntity.ok(foundTrainingRoutines);
    }

    @GetMapping(path = "{userId}/workouts")
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

    @GetMapping(path = "{userId}/exercise-types")
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




