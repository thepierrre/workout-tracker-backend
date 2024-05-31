package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.repositories.WorkoutRepository;
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
                .orElseThrow(() -> new EntityNotFoundException("Exercise instance not found."));

        return exerciseInstance.getWorkingSets().stream()
                .map(set -> workingSetMapper.mapToDto(set)).toList();

    }

    public void deleteById(Short id) {

    }

    public void deleteAll() {
    }

    public void deleteById(UUID id) {
        workingSetRepository.deleteById(id);
    }

}
