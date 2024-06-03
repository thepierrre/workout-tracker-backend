package com.example.gymapp.helpers;

import com.example.gymapp.domain.entities.CategoryEntity;

import java.util.UUID;

public class CategoryDataHelper {

    public static CategoryEntity createCategoryEntity(String name) {
        UUID id = UUID.randomUUID();
        return CategoryEntity.builder()
                .name(name)
                .id(id)
                .build();
    }

}
