package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkingSetMapper implements Mapper<WorkingSetEntity, WorkingSetDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public WorkingSetDto mapTo(WorkingSetEntity workingSetEntity) {
        return modelMapper.map(workingSetEntity, WorkingSetDto.class);
    }

    @Override
    public WorkingSetEntity mapFrom(WorkingSetDto workingSetDto) {
        return modelMapper.map(workingSetDto, WorkingSetEntity.class);
    }

}
