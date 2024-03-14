package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapper implements Mapper<WorkoutEntity, WorkoutDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public WorkoutDto mapTo(WorkoutEntity workoutEntity) {
        return modelMapper.map(workoutEntity, WorkoutDto.class);
    }

    @Override
    public WorkoutEntity mapFrom(WorkoutDto workoutDto) {
        return modelMapper.map(workoutDto, WorkoutEntity.class);
    }
}
