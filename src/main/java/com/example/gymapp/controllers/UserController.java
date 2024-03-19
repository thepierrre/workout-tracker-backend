package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.services.UserService;
import com.example.gymapp.services.impl.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Array;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/users")
    public List<UserEntity> getAll() {
        return userService.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity createdUser = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/users")
    public ResponseEntity deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.update(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
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

    @GetMapping(path = "/users/{userId}/exercise-types")
    public ResponseEntity<List<ExerciseTypeEntity>> getExerciseTypesForUser(@PathVariable("userId") UUID userId) {
        try {
//            List<ExerciseTypeEntity> foundExerciseTypes = new ArrayList<ExerciseTypeEntity>(userService.getExerciseTypesForUser(userId));
//            return new ResponseEntity<>(new ArrayList<ExerciseTypeEntity>(userService.getExerciseTypesForUser(userId)), HttpStatus.OK);
            return (ResponseEntity<List<ExerciseTypeEntity>>) userService.getExerciseTypesForUser(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
//        } catch (ResponseStatusException ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("User not found for id:" + userId));
//        }

    }
}

// Fred 9766a1ff-87ae-4cfe-8d5d-45185d9790dc
// Max 6d8054aa-db4c-4bde-9128-516c22723f33

