package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.repositories.ExerciseRepository;
import com.example.gymapp.services.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private ExerciseRepository exerciseRepository;

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
    public void deleteById(Long id) {
        exerciseRepository.deleteById(String.valueOf(id));
    }

}
