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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private Mapper<RoutineEntity, RoutineDto> trainingTypeMapper;

    @PostMapping(path = "routines")
    public ResponseEntity<RoutineDto> createTrainingRoutine(@Valid @RequestBody RoutineDto trainingRoutineDto, @AuthenticationPrincipal UserDetails userDetails) {
        RoutineDto createdTrainingRoutine = routineService.createRoutine(trainingRoutineDto, userDetails.getUsername());
        return new ResponseEntity<>(createdTrainingRoutine, HttpStatus.CREATED);
    }

//    @GetMapping(path = "routines")
//    public ResponseEntity<List<RoutineDto>> findAll() {
//
//        List<RoutineDto> routines = routineService.findAll();
//        return new ResponseEntity<>(routines, HttpStatus.OK);
//    }

    @GetMapping(path = "user-routines")
    public ResponseEntity<List<RoutineDto>> findAllForUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<RoutineDto> routines = routineService.findAllForUser(userDetails.getUsername());
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }

    @PutMapping(path = "routines/{routineId}")
    public ResponseEntity<RoutineDto> updateById(
            @PathVariable("routineId") UUID routineId,
            @RequestBody RoutineDto routineDto
    ) {
        RoutineDto updatedRoutine = routineService.updateById(routineId, routineDto);
        return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
    }


    @DeleteMapping(path = "routines/{routineId}")
    public ResponseEntity<Void> deleteById(@PathVariable("routineId") UUID id) {
        routineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @DeleteMapping(path = "routines")
//    public ResponseEntity<Void> deleteAll() {
//        routineService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
