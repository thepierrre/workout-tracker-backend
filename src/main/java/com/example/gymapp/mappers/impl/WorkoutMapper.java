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

        Converter<LocalDateTime, String> localDateTimeToString = new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> mappingContext) {
                LocalDateTime dateTime = mappingContext.getSource();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)");
                return dateTime.format(formatter);
            }
        };

        // Register the converter with ModelMapper
        modelMapper.addConverter(localDateTimeToString);

        return modelMapper.map(workoutEntity, WorkoutDto.class);
    }

    @Override
    public WorkoutEntity mapFromDto(WorkoutDto workoutDto) {

        Converter<String, LocalDateTime> stringToLocalDateTime = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                String dateString = mappingContext.getSource();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)");
                return LocalDateTime.parse(dateString, formatter);
            }
        };


        // Register the converter with ModelMapper
        modelMapper.addConverter(stringToLocalDateTime);

        return modelMapper.map(workoutDto, WorkoutEntity.class);
    }
}
