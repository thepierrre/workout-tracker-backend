package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WorkoutMapper implements Mapper<WorkoutEntity, WorkoutDto> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    @Override
    public WorkoutDto mapToDto(WorkoutEntity workoutEntity) {

        WorkoutDto workoutDto = modelMapper.map(workoutEntity, WorkoutDto.class);

        // We need this because inside exerciseInstanceMapper, the working sets are sorted by time date.
        workoutDto.setExerciseInstances(workoutEntity.getExerciseInstances().stream()
                .map(exerciseInstanceMapper::mapToDto)
                .collect(Collectors.toList()));

        return workoutDto;
    }

    @Override
    public WorkoutEntity mapFromDto(WorkoutDto workoutDto) {
        return modelMapper.map(workoutDto, WorkoutEntity.class);
    }
}