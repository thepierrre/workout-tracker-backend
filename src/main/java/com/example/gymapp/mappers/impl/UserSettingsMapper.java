package com.example.gymapp.mappers.impl;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.example.gymapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserSettingsMapper implements Mapper<UserSettingsEntity, UserSettingsDto> {

    @Autowired
    ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<UserSettingsEntity, UserSettingsDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getUsername(), destination.getUsername());
            }
        });
    }

    @Override
    public UserSettingsDto mapToDto(UserSettingsEntity userSettingsEntity) {
        return modelMapper.map(userSettingsEntity, UserSettingsDto.class);
    }

    @Override
    public UserSettingsEntity mapFromDto(UserSettingsDto userSettingsDto) {
        return modelMapper.map(userSettingsDto, UserSettingsEntity.class);
    }
}
