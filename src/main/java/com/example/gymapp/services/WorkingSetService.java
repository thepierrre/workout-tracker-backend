package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;

import java.util.List;

public interface WorkingSetService {


    WorkingSetDto createWorkingSet(WorkingSetDto workingSetDto);
    List<WorkingSetDto> findAll();

    void deleteById(Short id);

    void deleteAll();

}
