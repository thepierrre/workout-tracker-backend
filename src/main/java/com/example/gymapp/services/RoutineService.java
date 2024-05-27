package com.example.gymapp.services;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineMapper routineMapper;

    @Autowired
    private UserRepository userRepository;

    public RoutineDto createRoutine(RoutineDto trainingRoutineDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        boolean routineExists = user.getRoutines().stream()
                .anyMatch(routine -> routine.getName().equalsIgnoreCase(trainingRoutineDto.getName()));

        if (routineExists) {
            throw new IllegalArgumentException(" A training routine with this name already exists.");
        }


        RoutineEntity routineEntity = routineMapper.mapFromDto(trainingRoutineDto);
        routineEntity.setUser(user);

        RoutineEntity savedRoutineEntity = routineRepository.save(routineEntity);
        user.getRoutines().add(savedRoutineEntity);
        userRepository.save(user);

        return routineMapper.mapToDto(savedRoutineEntity);



//        TrainingRoutineEntity trainingRoutineEntity = trainingRoutineMapper.mapFromDto(trainingRoutineDto);
//
//        trainingRoutineEntity.setExerciseTypes(trainingRoutineEntity.getExerciseTypes());
//
//        TrainingRoutineEntity savedTrainingRoutineEntity = trainingRoutineRepository.save(trainingRoutineEntity);
//
//        return trainingRoutineMapper.mapToDto(savedTrainingRoutineEntity);
    }

    public List<RoutineDto> findAll() {
        return routineRepository.
                findAll().stream().map(routineMapper::mapToDto).
                toList(); }

    public void deleteById(UUID id) {
        routineRepository.deleteById(id);
    }

    public void deleteAll() {
        routineRepository.deleteAll();
    }
}
