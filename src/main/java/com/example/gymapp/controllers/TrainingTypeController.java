package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.TrainingTypeDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.mappers.Mapper;

import com.example.gymapp.services.TrainingTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TrainingTypeController {

    @Autowired
    private TrainingTypeService trainingTypeService;

    @Autowired
    private Mapper<TrainingTypeEntity, TrainingTypeDto> trainingTypeMapper;

    @PostMapping(path = "/training-types")
    public ResponseEntity<TrainingTypeDto> createTrainingType(@Valid @RequestBody TrainingTypeDto trainingTypeDto) {
        TrainingTypeEntity trainingTypeEntity = trainingTypeMapper.mapFrom(trainingTypeDto);
        TrainingTypeEntity createdTrainingType = trainingTypeService.createTrainingType(trainingTypeEntity);

//        List<ExerciseEntity> exercises = new ArrayList<>();
//
//        for (ExerciseEntity exercise: trainingTypeEntity.getExercises()) {
//            ExerciseEntity exerciseEntity = new ExerciseEntity(exercise.getName());
//
//            exerciseEntity.setTrainingType(trainingTypeEntity);
//            exercises.add(exerciseEntity);
//        }
//
//        trainingTypeEntity.setExercises(exercises);

        return new ResponseEntity<>(trainingTypeMapper.mapTo(trainingTypeEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/training-types")
    public List<TrainingTypeEntity> getAll() {
        List<TrainingTypeEntity> foundTrainingTypes = trainingTypeService.findAll();
        return foundTrainingTypes;
    }

    @DeleteMapping(path = "/training-types/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        trainingTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/training-types")
    public ResponseEntity deleteAll() {
        trainingTypeService.deleteAll();
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
