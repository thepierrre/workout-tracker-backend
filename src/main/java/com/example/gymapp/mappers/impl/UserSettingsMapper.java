package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSettingsMapper implements Mapper<UserSettingsEntity, UserSettingsDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserSettingsDto mapToDto(UserSettingsEntity userSettingsEntity) {
        return modelMapper.map(userSettingsEntity, UserSettingsDto.class);
    }

    @Override
    public UserSettingsEntity mapFromDto(UserSettingsDto userSettingsDto) {
        return modelMapper.map(userSettingsDto, UserSettingsEntity.class);
    }
}
