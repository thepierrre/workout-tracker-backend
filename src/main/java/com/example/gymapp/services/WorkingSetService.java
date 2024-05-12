package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.WorkingSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkingSetService {


    @Autowired
    WorkingSetRepository workingSetRepository;

    @Autowired
    WorkingSetMapper workingSetMapper;

    public WorkingSetDto createWorkingSet(WorkingSetDto workingSetDto) {
        WorkingSetEntity createdWorkingSet = workingSetRepository.save(workingSetMapper.mapFromDto(workingSetDto));
        return workingSetMapper.mapToDto(createdWorkingSet);
    }

    public List<WorkingSetDto> findAll() {
        return workingSetRepository.findAll().stream().map(workingSetMapper::mapToDto).toList();
    }

    public void deleteById(Short id) {

    }

    public void deleteAll() {
    }

    public void deleteById(UUID id) {
        workingSetRepository.deleteById(id);
    }

}
