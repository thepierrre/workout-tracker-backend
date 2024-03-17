package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.services.ExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseTypeServiceImpl implements ExerciseTypeService {
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    TrainingRoutineMapper trainingRoutineMapper;

    public ExerciseTypeServiceImpl(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @Override
    public ExerciseTypeEntity createExercise(ExerciseTypeEntity exerciseTypeEntity) {
        return exerciseTypeRepository.save(exerciseTypeEntity);
    }

    @Override
    public List<ExerciseTypeEntity> findAll() {
        return exerciseTypeRepository.findAll();
    }


    @Override
    public void deleteById(UUID id) {
        exerciseTypeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        exerciseTypeRepository.deleteAll();
    }

    @Override
    public boolean isExists(UUID id) {
        return exerciseTypeRepository.existsById(id);
    }

    @Override
    public ExerciseTypeEntity update(UUID id, ExerciseTypeEntity exerciseTypeEntity) {
        exerciseTypeEntity.setId(id);
        return exerciseTypeRepository.findById(id).map(existingExercise -> {
            Optional.ofNullable(exerciseTypeEntity.getName()).ifPresent(existingExercise::setName);
            return exerciseTypeRepository.save(existingExercise);
        }).orElseThrow(() -> new RuntimeException("Exercise does not exist"));
    }
}
