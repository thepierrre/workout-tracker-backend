package com.example.gymapp.services;

import com.example.gymapp.domain.entities.TrainingTypeEntity;
import java.util.List;
import java.util.UUID;

public interface TrainingTypeService {

    TrainingTypeEntity createTrainingType(TrainingTypeEntity trainingTypeEntity);
    List<TrainingTypeEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();
}
