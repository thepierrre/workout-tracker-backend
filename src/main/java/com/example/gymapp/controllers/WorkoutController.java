package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    WorkoutMapper workoutMapper;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<WorkoutDto> findAll() {
        return workoutService.findAll();
    }

    @GetMapping(path = "{workoutId}")
    public ResponseEntity<WorkoutDto> findById(@PathVariable("workoutId") UUID id) {
        Optional<WorkoutEntity> workout = workoutService.findById(id);
        if (workout.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            WorkoutDto foundWorkout = workoutMapper.mapToDto(workout.get());
            return new ResponseEntity<>(foundWorkout, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> createWorkout(@Valid @RequestBody WorkoutDto workoutDto, @AuthenticationPrincipal UserDetails userDetails) {




        WorkoutDto createdWorkout = workoutService.createWorkout(workoutDto, userDetails.getUsername());
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteById(@PathVariable("workoutId") UUID id) {
        try {
            workoutService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        workoutService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
