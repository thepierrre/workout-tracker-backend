package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.BlueprintWorkingSetDto;
import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.BlueprintWorkingSetEntity;
import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlueprintWorkingSetMapper implements Mapper<BlueprintWorkingSetEntity, BlueprintWorkingSetDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BlueprintWorkingSetDto mapToDto(BlueprintWorkingSetEntity blueprintWorkingSetEntity) {
        return modelMapper.map(blueprintWorkingSetEntity, BlueprintWorkingSetDto.class);
    }

    @Override
    public BlueprintWorkingSetEntity mapFromDto(BlueprintWorkingSetDto blueprintWorkingSetDto) {
        return modelMapper.map(blueprintWorkingSetDto, BlueprintWorkingSetEntity.class);
    }
}
