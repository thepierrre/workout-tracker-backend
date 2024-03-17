package com.example.gymapp.services;

import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import java.util.List;
import java.util.UUID;

public interface TrainingRoutineService {

    TrainingRoutineEntity createTrainingType(TrainingRoutineEntity trainingRoutineEntity);
    List<TrainingRoutineEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();
}
