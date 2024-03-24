package com.example.gymapp.services.impl;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.services.WorkingSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingSetServiceImpl implements WorkingSetService {

    @Autowired
    WorkingSetRepository workingSetRepository;

    @Autowired
    WorkingSetMapper workingSetMapper;

    @Override
    public WorkingSetDto createWorkingSet(WorkingSetDto workingSetDto) {
        WorkingSetEntity createdWorkingSet = workingSetRepository.save(workingSetMapper.mapFromDto(workingSetDto));
        return workingSetMapper.mapToDto(createdWorkingSet);
    }

    @Override
    public List<WorkingSetDto> findAll() {
        return workingSetRepository.findAll().stream().map(workingSetMapper::mapToDto).toList();
    }

    @Override
    public void deleteById(Short id) {

    }

    @Override
    public void deleteAll() {

    }
}

