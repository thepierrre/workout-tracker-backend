package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.mappers.Mapper;

import com.example.gymapp.services.RoutineService;
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
@RequestMapping("/api/routines")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private Mapper<RoutineEntity, RoutineDto> trainingTypeMapper;

    @PostMapping
    public ResponseEntity<RoutineDto> createTrainingRoutine(@Valid @RequestBody RoutineDto trainingRoutineDto, @AuthenticationPrincipal UserDetails userDetails) {
        RoutineDto createdTrainingRoutine = routineService.createRoutine(trainingRoutineDto, userDetails.getUsername());
        return new ResponseEntity<>(createdTrainingRoutine, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RoutineDto> getAll() {
        return routineService.findAll();
    }

    @DeleteMapping(path = "{routineId}")
    public ResponseEntity<Void> deleteById(@PathVariable("routineId") UUID id) {
        routineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        routineService.deleteAll();
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
