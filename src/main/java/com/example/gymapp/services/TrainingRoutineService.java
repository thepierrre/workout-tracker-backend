package com.example.gymapp.services;

import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import java.util.List;

public interface TrainingRoutineService {

    TrainingRoutineEntity createTrainingType(TrainingRoutineEntity trainingRoutineEntity);
    List<TrainingRoutineEntity> findAll();

    void deleteById(Long id);

    void deleteAll();
}
