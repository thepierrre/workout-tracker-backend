package com.example.gymapp.config;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.UserEntity;
import org.hibernate.collection.spi.PersistentList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setCollectionsMergeEnabled(false);

        modelMapper.typeMap(UserEntity.class, UserDto.class).addMappings(mapper -> {
            mapper.map(UserEntity::getId, UserDto::setId);
            mapper.map(UserEntity::getUsername, UserDto::setUsername);
            mapper.skip(UserDto::setPassword);
        });

        modelMapper.addConverter(ctx -> new ArrayList<>(ctx.getSource()), PersistentList.class, List.class);


        return modelMapper;
    }
}
