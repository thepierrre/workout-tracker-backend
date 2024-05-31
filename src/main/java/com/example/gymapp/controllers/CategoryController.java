package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        categoryService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable("categoryId") UUID categoryId) {
        categoryService.deleteById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }
}
