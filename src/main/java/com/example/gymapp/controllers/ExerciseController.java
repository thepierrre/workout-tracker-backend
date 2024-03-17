package com.example.gymapp.controllers;


import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private Mapper<ExerciseTypeEntity, ExerciseTypeDto> exerciseMapper;

    @GetMapping(path = "/exercises")
    public List<ExerciseTypeEntity> findAll() {
        return exerciseService.findAll();
    }

    @PostMapping(path = "/exercises")
    public ResponseEntity<ExerciseTypeDto> createExercise(@Valid @RequestBody ExerciseTypeDto exerciseTypeDto) {
        ExerciseTypeEntity exerciseTypeEntity = exerciseMapper.mapFrom(exerciseTypeDto);
        ExerciseTypeEntity createdExerciseTypeEntity = exerciseService.createExercise(exerciseTypeEntity);
        return new ResponseEntity<>(exerciseMapper.mapTo(createdExerciseTypeEntity), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/exercises/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        exerciseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/exercises")
    public ResponseEntity deleteAll() {
        exerciseService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseTypeDto> update(
            @PathVariable("id") Long id,
            @RequestBody ExerciseTypeDto exerciseTypeDto
    ) {
        if(!exerciseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ExerciseTypeEntity exerciseTypeEntity = exerciseMapper.mapFrom(exerciseTypeDto);
        ExerciseTypeEntity updatedExercise = exerciseService.update(id, exerciseTypeEntity);
        return new ResponseEntity<>(exerciseMapper.mapTo(updatedExercise), HttpStatus.OK);
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
