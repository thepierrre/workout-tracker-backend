package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseTypeMapper implements Mapper<ExerciseTypeEntity, ExerciseTypeDto> {

    @Autowired
    ModelMapper modelMapper;

//    Converter<String, RepsOrTimed> stringToRepsOrTimed = new Converter<>() {
//        @Override
//        public RepsOrTimed convert(MappingContext<String, RepsOrTimed> context) {
//            return context.getSource() == null ? null : RepsOrTimed.valueOf(context.getSource().toUpperCase());
//        }
//    };
//
//    Converter<RepsOrTimed, String> repsOrTimedToString = new Converter<>() {
//        @Override
//        public String convert(MappingContext<RepsOrTimed, String> context) {
//            return context.getSource() == null ? null : context.getSource().name.toLowerCase();
//        }
//    };

    @Override
    public ExerciseTypeDto mapToDto(ExerciseTypeEntity exerciseTypeEntity) {
        //modelMapper.addConverter(repsOrTimedToString);
        return modelMapper.map(exerciseTypeEntity, ExerciseTypeDto.class);
    }

    @Override
    public ExerciseTypeEntity mapFromDto(ExerciseTypeDto exerciseTypeDto) {
        //modelMapper.addConverter(stringToRepsOrTimed);
        return modelMapper.map(exerciseTypeDto, ExerciseTypeEntity.class);
    }

}
