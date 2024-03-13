package com.example.gymapp.controllers;


import com.example.gymapp.domain.dto.ExerciseDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private Mapper<ExerciseEntity, ExerciseDto> exerciseMapper;

//    public ExerciseController(ExerciseService exerciseService, Mapper<ExerciseEntity, ExerciseDto> exerciseMapper) {
//        this.exerciseService = exerciseService;
//        this.exerciseMapper = exerciseMapper;
//    }

    @PostMapping(path = "/exercises")
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto) {
        ExerciseEntity exerciseEntity = exerciseMapper.mapFrom(exerciseDto);
        ExerciseEntity createdExerciseEntity = exerciseService.createExercise(exerciseEntity);
        return new ResponseEntity<>(exerciseMapper.mapTo(createdExerciseEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/exercises")
    public List<ExerciseEntity> findAll() {
        List<ExerciseEntity> foundExercises = exerciseService.findAll();
        return foundExercises;

    }

    @DeleteMapping(path = "/exercises/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        exerciseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseDto> update(
            @PathVariable("id") UUID id,
            @RequestBody ExerciseDto exerciseDto
    ) {
        if(!exerciseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ExerciseEntity exerciseEntity = exerciseMapper.mapFrom(exerciseDto);
        ExerciseEntity updatedExercise = exerciseService.update(id, exerciseEntity);
        return new ResponseEntity<>(exerciseMapper.mapTo(updatedExercise), HttpStatus.OK);
    }
}
