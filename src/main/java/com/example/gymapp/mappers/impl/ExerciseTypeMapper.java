package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseTypeMapper implements Mapper<ExerciseTypeEntity, ExerciseTypeDto> {

    @Autowired
    ModelMapper modelMapper;


    @Override
    public ExerciseTypeDto mapTo(ExerciseTypeEntity exerciseTypeEntity) {
        return modelMapper.map(exerciseTypeEntity, ExerciseTypeDto.class);
    }

    @Override
    public ExerciseTypeEntity mapFrom(ExerciseTypeDto exerciseTypeDto) {
        return modelMapper.map(exerciseTypeDto, ExerciseTypeEntity.class);
    }

}
