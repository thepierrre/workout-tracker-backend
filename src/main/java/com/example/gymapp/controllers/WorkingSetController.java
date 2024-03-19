package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.services.WorkingSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkingSetController {

    @Autowired
    WorkingSetService workingSetService;

    @Autowired
    WorkingSetMapper workingSetMapper;

    @GetMapping(path = "/working-sets")
    public List<WorkingSetEntity> findAll() {
        return workingSetService.findAll();
    }

    @PostMapping(path = "/working-sets")
    public ResponseEntity<WorkingSetDto> createWorkingSet(@RequestBody WorkingSetDto workingSetDto) {
        WorkingSetEntity workingSetEntity = workingSetMapper.mapFrom(workingSetDto);
        WorkingSetEntity createdWorkingSet = workingSetService.createWorkingSet(workingSetEntity);
        return new ResponseEntity<>(workingSetMapper.mapTo(workingSetEntity), HttpStatus.CREATED);
    }
}
