package com.example.gymapp.services;

import com.example.gymapp.domain.entities.WorkingSetEntity;

import java.util.List;

public interface WorkingSetService {


    WorkingSetEntity createWorkingSet(WorkingSetEntity workingSetEntity);
    List<WorkingSetEntity> findAll();

    void deleteById(Short id);

    void deleteAll();

}
