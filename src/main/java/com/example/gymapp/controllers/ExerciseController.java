package com.example.gymapp.controllers;


import com.example.gymapp.domain.dto.ExerciseDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

    private ExerciseService exerciseService;
    private Mapper<ExerciseEntity, ExerciseDto> exerciseMapper;

    public ExerciseController(ExerciseService exerciseService, Mapper<ExerciseEntity, ExerciseDto> exerciseMapper) {
        this.exerciseService = exerciseService;
        this.exerciseMapper = exerciseMapper;
    }

    @PostMapping(path = "/exercises")
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto) {
        ExerciseEntity exerciseEntity = exerciseMapper.mapFrom(exerciseDto);
        ExerciseEntity createdExerciseEntity = exerciseService.save(exerciseEntity);
        return new ResponseEntity<>(exerciseMapper.mapTo(createdExerciseEntity), HttpStatus.CREATED);
    }
}
