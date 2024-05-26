package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.mappers.Mapper;

import com.example.gymapp.services.TrainingRoutineService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/routines")
public class TrainingRoutineController {

    @Autowired
    private TrainingRoutineService trainingRoutineService;

    @Autowired
    private Mapper<TrainingRoutineEntity, TrainingRoutineDto> trainingTypeMapper;

    @PostMapping
    public ResponseEntity<TrainingRoutineDto> createTrainingRoutine(@Valid @RequestBody TrainingRoutineDto trainingRoutineDto, @AuthenticationPrincipal UserDetails userDetails) {
        TrainingRoutineDto createdTrainingRoutine = trainingRoutineService.createTrainingType(trainingRoutineDto, userDetails.getUsername());
        return new ResponseEntity<>(createdTrainingRoutine, HttpStatus.CREATED);
    }

    @GetMapping
    public List<TrainingRoutineDto> getAll() {
        return trainingRoutineService.findAll();
    }

    @DeleteMapping(path = "{routineId}")
    public ResponseEntity<Void> deleteById(@PathVariable("routineId") UUID id) {
        trainingRoutineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        trainingRoutineService.deleteAll();
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
