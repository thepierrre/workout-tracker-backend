package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryDataHelper {

    public static CategoryEntity createCategoryEntity(String name) {
        UUID id = UUID.randomUUID();
        return CategoryEntity.builder()
                .id(id)
                .name(name)
                .exerciseTypes(new ArrayList<>())
                .build();
    }

    public static CategoryDto createCategoryRequestDto(String name) {
        return CategoryDto.builder()
                .name(name)
                .build();
    }

    public static CategoryDto createCategoryResponseDto(String name) {
        UUID id = UUID.randomUUID();
        return CategoryDto.builder()
                .id(id)
                .name(name)
                .exerciseTypes(new ArrayList<>())
                .build();
    }

}
