package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineExerciseDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineExerciseEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoutineExerciseMapper implements Mapper<RoutineExerciseEntity, RoutineExerciseDto> {

    @Autowired
    ModelMapper modelMapper;


    @Override
    public RoutineExerciseDto mapToDto(RoutineExerciseEntity routineExerciseEntity) {
        return modelMapper.map(routineExerciseEntity, RoutineExerciseDto.class);
    }

    @Override
    public RoutineExerciseEntity mapFromDto(RoutineExerciseDto routineExerciseDto) {
        return modelMapper.map(routineExerciseDto, RoutineExerciseEntity.class);
    }
}
