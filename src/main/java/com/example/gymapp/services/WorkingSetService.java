package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.WorkingSetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkingSetService {

    @Autowired
    WorkingSetRepository workingSetRepository;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    WorkingSetMapper workingSetMapper;

    public WorkingSetDto createWorkingSet(WorkingSetDto workingSetDto) {
        WorkingSetEntity createdWorkingSet = workingSetRepository.save(workingSetMapper.mapFromDto(workingSetDto));
        return workingSetMapper.mapToDto(createdWorkingSet);
    }

    public List<WorkingSetDto> findAll() {
        return workingSetRepository.findAll().stream()
                .map(set -> workingSetMapper.mapToDto(set)).toList();
    }

    public List<WorkingSetDto> findAllForExerciseInstance(UUID exerciseInstanceId) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        return exerciseInstance.getWorkingSets().stream()
                .map(set -> workingSetMapper.mapToDto(set)).toList();
    }

    public WorkingSetDto patchById(UUID setId, WorkingSetDto workingSetDto) {
        WorkingSetEntity workingSet = workingSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        if (workingSetDto.getReps() != null) {
            workingSet.setReps(workingSetDto.getReps());
        }

        if (workingSetDto.getWeight() != null) {
            workingSet.setWeight(workingSetDto.getWeight());
        }

        workingSetRepository.save(workingSet);

        return workingSetMapper.mapToDto(workingSet);
    }

    public void deleteById(UUID setId) {
        WorkingSetEntity set = workingSetRepository.findById(setId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        workingSetRepository.deleteById(setId);
    }

}
