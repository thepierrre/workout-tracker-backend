package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.services.ExerciseInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseInstanceServiceImpl implements ExerciseInstanceService {

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Override
    public List<ExerciseInstanceEntity> findAll() {
        return exerciseInstanceRepository.findAll();
    }

    @Override
    public ExerciseInstanceEntity createExerciseInstance(ExerciseInstanceEntity exerciseInstanceEntity) {
        return exerciseInstanceRepository.save(exerciseInstanceEntity);
    }
}
