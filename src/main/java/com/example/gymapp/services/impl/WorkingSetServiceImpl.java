package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.services.WorkingSetService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WorkingSetServiceImpl implements WorkingSetService {

    @Autowired
    WorkingSetService workingSetService;

    @Autowired
    WorkingSetRepository workingSetRepository;

    @Override
    public WorkingSetEntity createWorkingSet(WorkingSetEntity workingSetEntity) {
        return null;
    }

    @Override
    public List<WorkingSetEntity> findAll() {
        return null;
    }

    @Override
    public void deleteById(Short id) {

    }

    @Override
    public void deleteAll() {

    }
}

