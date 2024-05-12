package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity createdCategory = categoryRepository.save(categoryMapper.mapFromDto(categoryDto));
        return categoryMapper.mapToDto(createdCategory);
    }


    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::mapToDto).toList();
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }
}
