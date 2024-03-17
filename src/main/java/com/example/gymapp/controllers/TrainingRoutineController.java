package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.mappers.Mapper;

import com.example.gymapp.services.TrainingRoutineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TrainingRoutineController {

    @Autowired
    private TrainingRoutineService trainingRoutineService;

    @Autowired
    private Mapper<TrainingRoutineEntity, TrainingRoutineDto> trainingTypeMapper;

    @PostMapping(path = "/training-routines")
    public ResponseEntity<TrainingRoutineDto> createTrainingType(@Valid @RequestBody TrainingRoutineDto trainingRoutineDto) {
        TrainingRoutineEntity trainingRoutineEntity = trainingTypeMapper.mapFrom(trainingRoutineDto);
        TrainingRoutineEntity createdTrainingType = trainingRoutineService.createTrainingType(trainingRoutineEntity);

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

        return new ResponseEntity<>(trainingTypeMapper.mapTo(trainingRoutineEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/training-routines")
    public List<TrainingRoutineEntity> getAll() {
        List<TrainingRoutineEntity> foundTrainingTypes = trainingRoutineService.findAll();
        return foundTrainingTypes;
    }

    @DeleteMapping(path = "/training-routines/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        trainingRoutineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/training-routines")
    public ResponseEntity deleteAll() {
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
