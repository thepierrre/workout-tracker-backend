package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.services.WorkingSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingSetServiceImpl implements WorkingSetService {

    @Autowired
    WorkingSetRepository workingSetRepository;

    @Override
    public WorkingSetEntity createWorkingSet(WorkingSetEntity workingSetEntity) {
        return workingSetRepository.save(workingSetEntity);
    }

    @Override
    public List<WorkingSetEntity> findAll() {
        return workingSetRepository.findAll();
    }

    @Override
    public void deleteById(Short id) {

    }

    @Override
    public void deleteAll() {

    }
}

