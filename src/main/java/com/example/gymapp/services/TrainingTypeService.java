package com.example.gymapp.services;

import com.example.gymapp.domain.entities.TrainingTypeEntity;
import java.util.List;

public interface TrainingTypeService {

    TrainingTypeEntity createTrainingType(TrainingTypeEntity trainingTypeEntity);

    List<TrainingTypeEntity> findAll();
}
