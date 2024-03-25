package com.example.gymapp.services.impl;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TrainingRoutineMapper trainingRoutineMapper;

    @Autowired
    private ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    private WorkoutMapper workoutMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        String email = userDto.getEmail();
        List<UserEntity> usersWithEmail = userRepository.findByEmail(email);

        if (!usersWithEmail.isEmpty()) {
            throw new IllegalArgumentException("User with the provided email already exists");
        }

        UserEntity createdUser = userRepository.save(userMapper.mapFromDto(userDto));
        return userMapper.mapToDto(createdUser);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public boolean isExists(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDto update(UUID id, UserDto userDto) {
        userDto.setId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userDto.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(userDto.getPassword()).ifPresent(existingUser::setPassword);
            userRepository.save(existingUser);
            return userMapper.mapToDto(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist."));
    }


    @Override
    public List<TrainingRoutineDto> getTrainingRoutinesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return userEntity.get().getTrainingRoutines().stream().map(trainingRoutineMapper::mapToDto).toList();
    }

    @Override
    public List<ExerciseTypeDto> getExerciseTypesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntity.get().getExerciseTypes().stream().map(exerciseTypeMapper::mapToDto).toList();
        }

    @Override
    public List<WorkoutDto> getWorkoutsForUser(UUID id) {
        return null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> NotFoundHandler(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> BadRequestHandler(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(Map.of("message", e.getMessage()));
    }


}


