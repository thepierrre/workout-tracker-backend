package com.example.gymapp.services;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.repositories.TrainingRoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingRoutineService {

    @Autowired
    private TrainingRoutineRepository trainingRoutineRepository;

    @Autowired
    private TrainingRoutineMapper trainingRoutineMapper;

    @Autowired
    private UserRepository userRepository;

    public TrainingRoutineDto createTrainingType(TrainingRoutineDto trainingRoutineDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        TrainingRoutineEntity trainingRoutineEntity = trainingRoutineMapper.mapFromDto(trainingRoutineDto);
        trainingRoutineEntity.setUser(user);

        TrainingRoutineEntity savedTrainingRoutineEntity = trainingRoutineRepository.save(trainingRoutineEntity);
        user.getTrainingRoutines().add(savedTrainingRoutineEntity);
        userRepository.save(user);

        return trainingRoutineMapper.mapToDto(savedTrainingRoutineEntity);



//        TrainingRoutineEntity trainingRoutineEntity = trainingRoutineMapper.mapFromDto(trainingRoutineDto);
//
//        trainingRoutineEntity.setExerciseTypes(trainingRoutineEntity.getExerciseTypes());
//
//        TrainingRoutineEntity savedTrainingRoutineEntity = trainingRoutineRepository.save(trainingRoutineEntity);
//
//        return trainingRoutineMapper.mapToDto(savedTrainingRoutineEntity);
    }

    public List<TrainingRoutineDto> findAll() {
        return trainingRoutineRepository.
                findAll().stream().map(trainingRoutineMapper::mapToDto).
                toList(); }

    public void deleteById(UUID id) {
        trainingRoutineRepository.deleteById(id);
    }

    public void deleteAll() {
        trainingRoutineRepository.deleteAll();
    }
}
