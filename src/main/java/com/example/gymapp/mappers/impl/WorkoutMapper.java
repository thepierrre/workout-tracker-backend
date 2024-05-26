package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class WorkoutMapper implements Mapper<WorkoutEntity, WorkoutDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public WorkoutDto mapToDto(WorkoutEntity workoutEntity) {
        return modelMapper.map(workoutEntity, WorkoutDto.class);
    }

    @Override
    public WorkoutEntity mapFromDto(WorkoutDto workoutDto) {
        return modelMapper.map(workoutDto, WorkoutEntity.class);
    }
}