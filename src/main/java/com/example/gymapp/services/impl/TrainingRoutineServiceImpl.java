package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.repositories.TrainingRoutineRepository;
import com.example.gymapp.services.TrainingRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingRoutineServiceImpl implements TrainingRoutineService {

    @Autowired
    private TrainingRoutineRepository trainingRoutineRepository;

    @Override
    public TrainingRoutineEntity createTrainingType(TrainingRoutineEntity trainingRoutineEntity) {

        for (ExerciseTypeEntity exerciseTypeEntity : trainingRoutineEntity.getExercises()) {
            exerciseTypeEntity.setTrainingType(trainingRoutineEntity);
        }
        trainingRoutineEntity.setExercises(trainingRoutineEntity.getExercises());

        return trainingRoutineRepository.save(trainingRoutineEntity);
    }

    @Override
    public List<TrainingRoutineEntity> findAll() { return trainingRoutineRepository.findAll(); }

    @Override
    public void deleteById(Long id) {
        trainingRoutineRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        trainingRoutineRepository.deleteAll();
    }
}
