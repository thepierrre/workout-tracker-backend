package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingRoutineMapper implements Mapper<TrainingRoutineEntity, TrainingRoutineDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public TrainingRoutineDto mapToDto(TrainingRoutineEntity trainingRoutineEntity) {
        return modelMapper.map(trainingRoutineEntity, TrainingRoutineDto.class);
    }

    @Override
    public TrainingRoutineEntity mapFromDto(TrainingRoutineDto trainingRoutineDto) {
        return modelMapper.map(trainingRoutineDto, TrainingRoutineEntity.class);
    }

}
