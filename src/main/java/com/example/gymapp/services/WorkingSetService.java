package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkingSetDto;

import java.util.List;
import java.util.UUID;

public interface WorkingSetService {


    WorkingSetDto createWorkingSet(WorkingSetDto workingSetDto);
    List<WorkingSetDto> findAll();

    void deleteById(Short id);

    void deleteAll();

    void deleteById(UUID id);

}
