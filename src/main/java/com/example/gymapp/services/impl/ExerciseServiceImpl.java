package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.impl.TrainingRoutineMapper;
import com.example.gymapp.repositories.ExerciseRepository;
import com.example.gymapp.mappers.impl.ExerciseMapper;
import com.example.gymapp.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseMapper exerciseMapper;

    @Autowired
    TrainingRoutineMapper trainingRoutineMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExerciseTypeEntity createExercise(ExerciseTypeEntity exerciseTypeEntity) {
        return exerciseRepository.save(exerciseTypeEntity);
    }

    @Override
    public List<ExerciseTypeEntity> findAll() {
        return exerciseRepository.findAll();
    }


    @Override
    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        exerciseRepository.deleteAll();
    }

    @Override
    public boolean isExists(Long id) {
        return exerciseRepository.existsById(id);
    }

    @Override
    public ExerciseTypeEntity update(Long id, ExerciseTypeEntity exerciseTypeEntity) {
        exerciseTypeEntity.setId(id);
        return exerciseRepository.findById(id).map(existingExercise -> {
            Optional.ofNullable(exerciseTypeEntity.getName()).ifPresent(existingExercise::setName);
            return exerciseRepository.save(existingExercise);
        }).orElseThrow(() -> new RuntimeException("Exercise does not exist"));
    }
}
