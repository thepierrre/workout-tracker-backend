package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.services.RoutineService;
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
public class RoutineController {

    @Autowired
    private RoutineService routineService;


    @PostMapping(path = "routines")
    public ResponseEntity<RoutineDto> createTrainingRoutine(@Valid @RequestBody RoutineDto trainingRoutineDto, @AuthenticationPrincipal UserDetails userDetails) {
        RoutineDto createdTrainingRoutine = routineService.createRoutine(trainingRoutineDto, userDetails.getUsername());
        return new ResponseEntity<>(createdTrainingRoutine, HttpStatus.CREATED);
    }

    @GetMapping(path = "user-routines")
    public ResponseEntity<List<RoutineDto>> findAllForUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<RoutineDto> routines = routineService.findAllForUser(userDetails.getUsername());
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }

    @PutMapping(path = "routines/{routineId}")
    public ResponseEntity<RoutineDto> updateById(
            @PathVariable("routineId") UUID routineId,
            @RequestBody RoutineDto routineDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        RoutineDto updatedRoutine = routineService.updateById(routineId, routineDto, userDetails.getUsername());
        return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
    }


    @DeleteMapping(path = "routines/{routineId}")
    public ResponseEntity<Void> deleteById(@PathVariable("routineId") UUID id) {
        routineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
