package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.TrainingTypeDto;
import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeMapper implements Mapper<TrainingTypeEntity, TrainingTypeDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public TrainingTypeDto mapTo(TrainingTypeEntity trainingTypeEntity) {
        return modelMapper.map(trainingTypeEntity, TrainingTypeDto.class);
    }

    @Override
    public TrainingTypeEntity mapFrom(TrainingTypeDto trainingTypeDto) {
        return modelMapper.map(trainingTypeDto, TrainingTypeEntity.class);
    }

}
