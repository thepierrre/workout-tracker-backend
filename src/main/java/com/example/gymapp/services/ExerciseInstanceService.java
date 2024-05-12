package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExerciseInstanceService {

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    public List<ExerciseInstanceDto> findAll() {
        return exerciseInstanceRepository.findAll().stream().map(exerciseInstanceMapper::mapToDto).toList();
    }

    public ExerciseInstanceDto createExerciseInstance(ExerciseInstanceDto exerciseInstanceDto) {
        ExerciseInstanceEntity exerciseInstanceEntity = exerciseInstanceMapper.mapFromDto(exerciseInstanceDto);
        exerciseInstanceRepository.save(exerciseInstanceEntity);
        return exerciseInstanceMapper.mapToDto(exerciseInstanceEntity);
    }

    public void deleteAll() {
        exerciseInstanceRepository.deleteAll();
    }

    public void deleteById(UUID id) {
        exerciseInstanceRepository.deleteById(id);
    }
}
