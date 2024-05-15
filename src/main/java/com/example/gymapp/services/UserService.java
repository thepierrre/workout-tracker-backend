package com.example.gymapp.services;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

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

    public UserDto createUser(UserDto userDto) {

        String email = userDto.getEmail();
        List<UserEntity> usersWithEmail = userRepository.findByEmail(email);

        if (!usersWithEmail.isEmpty()) {
            throw new IllegalArgumentException("User with the provided email already exists");
        }

//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setPassword(userDto.getPassword());

        UserEntity createdUser = userRepository.save(userMapper.mapFromDto(userDto));
        return userMapper.mapToDto(createdUser);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    public void deleteById(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public boolean isExists(UUID id) {
        return userRepository.existsById(id);
    }

    public UserDto update(UUID id, UserDto userDto) {
        userDto.setId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userDto.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(userDto.getPassword()).ifPresent(existingUser::setPassword);
            userRepository.save(existingUser);
            return userMapper.mapToDto(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist."));
    }


    public List<TrainingRoutineDto> getTrainingRoutinesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return userEntity.get().getTrainingRoutines().stream().map(trainingRoutineMapper::mapToDto).toList();
    }

    public List<ExerciseTypeDto> getExerciseTypesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntity.get().getExerciseTypes().stream().map(exerciseTypeMapper::mapToDto).toList();
    }

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
