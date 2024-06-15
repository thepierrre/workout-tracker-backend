package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.services.ExerciseInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ExerciseInstanceController {

    @Autowired
    ExerciseInstanceService exerciseInstanceService;

    @PostMapping(path = "exercise-instances/{exerciseInstanceId}/sets")
    public ResponseEntity<ExerciseInstanceDto> createWorkingSetForExercise(
            @PathVariable("exerciseInstanceId") UUID exerciseInstanceId,
            @RequestBody WorkingSetDto workingSetDto
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.
                createWorkingSetforExercise(exerciseInstanceId, workingSetDto);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PatchMapping(path = "exercise-instances/{exerciseInstanceId}/sets/{setId}")
    public ResponseEntity<ExerciseInstanceDto> updateWorkingSetById(
            @PathVariable("exerciseInstanceId") UUID exerciseInstanceId,
            @PathVariable("setId") Long setId,
            @RequestBody WorkingSetDto workingSetDto
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.updateWorkingSetById(exerciseInstanceId, setId, workingSetDto);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @DeleteMapping(path = "exercise-instances/{exerciseInstanceId}/sets/{setId}")
    public ResponseEntity<ExerciseInstanceDto> deleteWorkingSetById(
            @PathVariable("exerciseInstanceId") UUID exerciseInstanceId,
            @PathVariable("setId") Long setId
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.deleteWorkingSetById(exerciseInstanceId, setId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @DeleteMapping(path = "exercise-instances/{exerciseInstanceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("exerciseInstanceId") UUID exerciseInstanceId) {
        exerciseInstanceService.deleteById(exerciseInstanceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
