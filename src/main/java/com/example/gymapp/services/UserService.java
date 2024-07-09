package com.example.gymapp.services;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.example.gymapp.mappers.impl.*;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSettingsMapper userSettingsMapper;

    public Optional<UserEntity> findByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        return Optional.ofNullable(user);
    }

    public UserSettingsDto getUserSettings(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        UserSettingsEntity userSettingsEntity = user.getUserSettings();

        return userSettingsMapper.mapToDto(userSettingsEntity);
    }

    public UserSettingsDto updateUserSettings(String username, UserSettingsDto userSettingsDto) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

//        if (userSettingsDto.getChangeThreshold() < 1) {
//            throw new IllegalArgumentException("The minimum allowed value is 1.");
//        }
//
//        if (userSettingsDto.getChangeThreshold() > 100) {
//            throw new IllegalArgumentException("The maximum allowed value is 100.");
//        }

        UserSettingsEntity userSettingsEntity = user.getUserSettings();
        userSettingsEntity.setChangeThreshold(userSettingsDto.getChangeThreshold());
        userSettingsRepository.save(userSettingsEntity);

        return userSettingsMapper.mapToDto(userSettingsEntity);
    }
}
