package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.repositories.TrainingTypeRepository;
import com.example.gymapp.services.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Override
    public TrainingTypeEntity createTrainingType(TrainingTypeEntity trainingTypeEntity) {
        return trainingTypeRepository.save(trainingTypeEntity);
    }

    @Override
    public List<TrainingTypeEntity> findAll() {
        return trainingTypeRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        trainingTypeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        trainingTypeRepository.deleteAll();
    }
}
