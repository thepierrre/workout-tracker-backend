package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseInstanceMapper implements Mapper<ExerciseInstanceEntity, ExerciseInstanceDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ExerciseInstanceDto mapToDto(ExerciseInstanceEntity exerciseInstanceEntity) {
        return modelMapper.map(exerciseInstanceEntity, ExerciseInstanceDto.class);
    }

    @Override
    public ExerciseInstanceEntity mapFromDto(ExerciseInstanceDto exerciseInstanceDto) {
        return modelMapper.map(exerciseInstanceDto, ExerciseInstanceEntity.class);
    }
}
