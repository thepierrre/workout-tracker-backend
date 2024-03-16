package com.example.gymapp.services.impl;

import com.example.gymapp.domain.dto.ExerciseDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.mappers.impl.TrainingTypeMapper;
import com.example.gymapp.repositories.ExerciseRepository;
import com.example.gymapp.mappers.impl.ExerciseMapper;
import com.example.gymapp.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseMapper exerciseMapper;

    @Autowired
    TrainingTypeMapper trainingTypeMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExerciseEntity createExercise(ExerciseEntity exerciseEntity) {
        return exerciseRepository.save(exerciseEntity);
    }

    @Override
    public List<ExerciseEntity> findAll() {
        return exerciseRepository.findAll();
    }


    @Override
    public void deleteById(UUID id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        exerciseRepository.deleteAll();
    }

    @Override
    public boolean isExists(UUID id) {
        return exerciseRepository.existsById(id);
    }

    @Override
    public ExerciseEntity update(UUID id, ExerciseEntity exerciseEntity) {
        exerciseEntity.setId(id);
        return exerciseRepository.findById(id).map(existingExercise -> {
            Optional.ofNullable(exerciseEntity.getName()).ifPresent(existingExercise::setName);
            return exerciseRepository.save(existingExercise);
        }).orElseThrow(() -> new RuntimeException("Exercise does not exist"));
    }
}
