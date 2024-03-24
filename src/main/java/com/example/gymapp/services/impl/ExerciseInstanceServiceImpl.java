package com.example.gymapp.services.impl;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
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
    public List<ExerciseInstanceDto> findAll() {
        return exerciseInstanceRepository.findAll().stream().map(exerciseInstanceMapper::mapToDto).toList();
    }

    @Override
    public ExerciseInstanceDto createExerciseInstance(ExerciseInstanceDto exerciseInstanceDto) {
        ExerciseInstanceEntity exerciseInstanceEntity = exerciseInstanceMapper.mapFromDto(exerciseInstanceDto);
        exerciseInstanceRepository.save(exerciseInstanceEntity);
        return exerciseInstanceMapper.mapToDto(exerciseInstanceEntity);
    }

    @Override
    public void deleteAll() {
        exerciseInstanceRepository.deleteAll();
    }
}
