package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkingSetMapper implements Mapper<InstanceWorkingSetEntity, InstanceWorkingSetDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public InstanceWorkingSetDto mapToDto(InstanceWorkingSetEntity instanceWorkingSetEntity) {
        return modelMapper.map(instanceWorkingSetEntity, InstanceWorkingSetDto.class);
    }

    @Override
    public InstanceWorkingSetEntity mapFromDto(InstanceWorkingSetDto instanceWorkingSetDto) {
        return modelMapper.map(instanceWorkingSetDto, InstanceWorkingSetEntity.class);
    }

}
