package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    TrainingRoutineMapper trainingRoutineMapper;

    public ExerciseTypeService(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto) {

        ExerciseTypeEntity exerciseTypeEntity = exerciseTypeMapper.mapFromDto(exerciseTypeDto);

        exerciseTypeEntity.setExerciseInstances(exerciseTypeEntity.getExerciseInstances());

        exerciseTypeRepository.save(exerciseTypeEntity);

        return exerciseTypeMapper.mapToDto(exerciseTypeEntity);

    }

    public List<ExerciseTypeEntity> findAll() {
        return exerciseTypeRepository.findAll();
    }

    public void deleteById(UUID id) {
        exerciseTypeRepository.deleteById(id);
    }

    public void deleteAll() {
        exerciseTypeRepository.deleteAll();
    }

    public boolean isExists(UUID id) {
        return exerciseTypeRepository.existsById(id);
    }

    public ExerciseTypeDto update(UUID id, ExerciseTypeDto exerciseTypeDto) {
        exerciseTypeDto.setId(id);
        return exerciseTypeRepository.findById(id).map(existingExercise -> {
            Optional.ofNullable(exerciseTypeDto.getName()).ifPresent(existingExercise::setName);
            exerciseTypeRepository.save(existingExercise);
            return exerciseTypeMapper.mapToDto(existingExercise);
        }).orElseThrow(() -> new RuntimeException("Exercise does not exist"));
    }
}
