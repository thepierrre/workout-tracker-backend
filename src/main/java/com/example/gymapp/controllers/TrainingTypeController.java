package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.TrainingTypeDto;
import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.mappers.Mapper;

import com.example.gymapp.services.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TrainingTypeController {

    @Autowired
    private TrainingTypeService trainingTypeService;

    @Autowired
    private Mapper<TrainingTypeEntity, TrainingTypeDto> trainingTypeMapper;

    @PostMapping(path = "/training-types")
    public ResponseEntity<TrainingTypeDto> createTrainingType(@RequestBody TrainingTypeDto trainingTypeDto) {
        TrainingTypeEntity trainingTypeEntity = trainingTypeMapper.mapFrom(trainingTypeDto);
        TrainingTypeEntity createdTrainingType = trainingTypeService.createTrainingType(trainingTypeEntity);
        return new ResponseEntity<>(trainingTypeMapper.mapTo(trainingTypeEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/training-types")
    public List<TrainingTypeEntity> getAll() {
        List<TrainingTypeEntity> foundTrainingTypes = trainingTypeService.findAll();
        return foundTrainingTypes;
    }
}
