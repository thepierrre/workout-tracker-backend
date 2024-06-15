package com.example.gymapp.services;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
