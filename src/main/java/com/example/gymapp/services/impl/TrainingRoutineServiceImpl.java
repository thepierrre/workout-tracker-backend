package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.repositories.TrainingRoutineRepository;
import com.example.gymapp.services.TrainingRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingRoutineServiceImpl implements TrainingRoutineService {

    @Autowired
    private TrainingRoutineRepository trainingRoutineRepository;

    @Override
    public TrainingRoutineEntity createTrainingType(TrainingRoutineEntity trainingRoutineEntity) {

//        for (ExerciseTypeEntity exerciseTypeEntity : trainingRoutineEntity.getExercises()) {
//            exerciseTypeEntity.setTrainingType(trainingRoutineEntity);
//        }
        trainingRoutineEntity.setExerciseTypes(trainingRoutineEntity.getExerciseTypes());

        return trainingRoutineRepository.save(trainingRoutineEntity);
    }

    @Override
    public List<TrainingRoutineEntity> findAll() { return trainingRoutineRepository.findAll(); }

    @Override
    public void deleteById(UUID id) {
        trainingRoutineRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        trainingRoutineRepository.deleteAll();
    }
}
