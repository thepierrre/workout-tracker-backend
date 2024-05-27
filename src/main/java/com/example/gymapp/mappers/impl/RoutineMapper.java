package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoutineMapper implements Mapper<RoutineEntity, RoutineDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RoutineDto mapToDto(RoutineEntity routineEntity) {
        return modelMapper.map(routineEntity, RoutineDto.class);
    }

    @Override
    public RoutineEntity mapFromDto(RoutineDto trainingRoutineDto) {
        return modelMapper.map(trainingRoutineDto, RoutineEntity.class);
    }

}
