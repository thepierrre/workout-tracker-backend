package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseInstanceService;
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
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ExerciseInstanceController {

    @Autowired
    ExerciseInstanceService exerciseInstanceService;

    @Autowired
    private Mapper<ExerciseInstanceEntity, ExerciseInstanceDto> exerciseInstanceMapper;


    @GetMapping(path = "exercise-instances")
    public ResponseEntity<List<ExerciseInstanceDto>> findAll() {

        List<ExerciseInstanceDto> exerciseInstances = exerciseInstanceService.findAll();
        return new ResponseEntity<>(exerciseInstances, HttpStatus.OK);
    }

    @GetMapping(path = "workouts/{workoutId}/exercise-instances")
    public ResponseEntity<List<ExerciseInstanceDto>> findAllForWorkout(@PathVariable("workoutId") UUID workoutId) {

        List<ExerciseInstanceDto> exerciseInstances = exerciseInstanceService.findAllForWorkout(workoutId);

        return new ResponseEntity<>(exerciseInstances, HttpStatus.OK);

    }

    @PostMapping(path = "exercise-instances")
    public ResponseEntity<ExerciseInstanceDto> createExerciseInstance(@Valid @RequestBody ExerciseInstanceDto exerciseInstanceDto) {
        ExerciseInstanceDto createdExerciseInstance = exerciseInstanceService.createExerciseInstance(exerciseInstanceDto);
        return new ResponseEntity<>(createdExerciseInstance, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "exercise-instances")
    public ResponseEntity<Void> deleteAll() {
        exerciseInstanceService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "exercise-instances/{exerciseInstanceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("exerciseInstanceId") UUID exerciseInstanceId) {
        exerciseInstanceService.deleteById(exerciseInstanceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PatchMapping(path = "exercise-instances/{exerciseInstanceId}")
//    public ResponseEntity<ExerciseInstanceDto> patchById(@PathVariable("exerciseInstanceId") UUID exerciseInstanceId) {
//        ExerciseInstanceDto patchedExerciseInstance = exerciseInstanceService.patchById(exerciseInstanceId);
//        return new ResponseEntity<>(patchedExerciseInstance, HttpStatus.OK);
//    }

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
