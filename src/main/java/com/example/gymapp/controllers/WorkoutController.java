package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.services.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @GetMapping(path = "user-workouts")
    public ResponseEntity<List<WorkoutDto>> findAllForUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<WorkoutDto> workouts = workoutService.findAllForUser(userDetails.getUsername());
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @PostMapping(path = "workouts")
    public ResponseEntity<WorkoutDto> createWorkout(
            @Valid @RequestBody WorkoutDto workoutDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        WorkoutDto createdWorkout = workoutService.createWorkout(workoutDto, userDetails.getUsername());
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("workouts/{workoutId}")
    public ResponseEntity<Void> deleteById(@PathVariable("workoutId") UUID id) {
        //TODO
            //workoutService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
