package com.example.gymapp.services;

import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import java.util.List;
import java.util.UUID;

public interface TrainingRoutineService {

    TrainingRoutineDto createTrainingType(TrainingRoutineDto trainingRoutineDto);
    List<TrainingRoutineDto> findAll();

    void deleteById(UUID id);

    void deleteAll();
}
