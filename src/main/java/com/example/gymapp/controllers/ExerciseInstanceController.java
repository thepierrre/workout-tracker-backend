package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.services.ExerciseInstanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
            @RequestBody InstanceWorkingSetDto instanceWorkingSetDto
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.
                createWorkingSetforExercise(exerciseInstanceId, instanceWorkingSetDto);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PatchMapping(path = "exercise-instances/{exerciseInstanceId}/sets/{setId}")
    public ResponseEntity<ExerciseInstanceDto> updateWorkingSetById(
            @PathVariable("exerciseInstanceId") UUID exerciseInstanceId,
            @PathVariable("setId") UUID setId,
            @RequestBody InstanceWorkingSetDto instanceWorkingSetDto
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.updateWorkingSetById(exerciseInstanceId, setId, instanceWorkingSetDto);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @DeleteMapping(path = "exercise-instances/{exerciseInstanceId}/sets/{setId}")
    public ResponseEntity<ExerciseInstanceDto> deleteWorkingSetById(
            @PathVariable("exerciseInstanceId") UUID exerciseInstanceId,
            @PathVariable("setId") UUID setId
    ) {
        ExerciseInstanceDto exercise = exerciseInstanceService.deleteWorkingSetById(exerciseInstanceId, setId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @DeleteMapping(path = "exercise-instances/{exerciseInstanceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("exerciseInstanceId") UUID exerciseInstanceId) {
        exerciseInstanceService.deleteById(exerciseInstanceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/workouts/{workoutId}/exercise-instances")
    public ResponseEntity<ExerciseInstanceDto> addExerciseToWorkout(
            @Valid
            @RequestBody ExerciseTypeDto exerciseTypeDto,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("workoutId") UUID workoutId
    ) {
        ExerciseInstanceDto newExercise = exerciseInstanceService.addExerciseToWorkout(
                workoutId, userDetails.getUsername(), exerciseTypeDto);
        return new ResponseEntity<>(newExercise, HttpStatus.OK);
    }
}
