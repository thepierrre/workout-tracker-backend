package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper implements Mapper<ExerciseEntity, ExerciseDto> {

    ModelMapper modelMapper;

    public ExerciseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ExerciseDto mapTo(ExerciseEntity exercise) {
        return modelMapper.map(exercise, ExerciseDto.class);
    }

    @Override
    public ExerciseEntity mapFrom(ExerciseDto exerciseDto) {
        return modelMapper.map(exerciseDto, ExerciseEntity.class);
    }
}
