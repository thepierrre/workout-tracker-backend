package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.helpers.ExerciseTypeDataHelper;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExerciseTypeServiceTest {

    @Autowired
    ExerciseTypeService exerciseTypeService;

    @MockBean
    ExerciseTypeRepository exerciseTypeRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ExerciseTypeMapper exerciseTypeMapper;

    private TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();

        UserEntity user1 = testData.user1;

        testData.exerciseTypeEntity1.getCategories().add(testData.categoryEntity1);
        testData.exerciseTypeEntity1.getCategories().add(testData.categoryEntity2);
        testData.exerciseTypeEntity2.getCategories().add(testData.categoryEntity1);
        testData.exerciseTypeEntity2.getCategories().add(testData.categoryEntity3);

        user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        user1.getExerciseTypes().add(testData.exerciseTypeEntity2);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        exerciseTypeRepository.deleteAll();

    }

    @Test
    void createExercise_Success() {
        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(testData.user1));
        when(exerciseTypeRepository.save(testData.exerciseTypeEntity3))
                .thenReturn(testData.exerciseTypeEntity3);
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto3))
                .thenReturn(testData.exerciseTypeEntity3);
        when(exerciseTypeMapper.mapToDto(testData.exerciseTypeEntity3))
                .thenReturn(testData.exerciseTypeResponseDto3);

        ExerciseTypeDto result = exerciseTypeService
                .createExercise(testData.exerciseTypeRequestDto3, "user1");

        assertNotNull(result);
        assertEquals(testData.exerciseTypeResponseDto3, result);
    }

    @Test
    void createExercise_UserNotFound() {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                exerciseTypeService.createExercise(testData.exerciseTypeRequestDto3, "user3"));

        assertEquals("User with the username \"user3\" not found.", exception.getMessage());
    }

    @Test
    void createExercise_ExerciseAlreadyExists() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto1))
                .thenReturn(testData.exerciseTypeEntity1);
        when(exerciseTypeRepository.findByUserAndName(testData.user1, testData.exerciseTypeEntity1.getName()))
                .thenReturn(Optional.of(testData.exerciseTypeEntity1));

        ConflictException exception = assertThrows(ConflictException.class,
                () -> exerciseTypeService.createExercise(testData.exerciseTypeRequestDto1, "user1"));

        assertEquals("Exercise with the name 'exercise1' already exists.", exception.getMessage());
    }

    @Test
    void createExercise_CategoryNotFound() {
        testData.categoryRequestDto1.setId(UUID.randomUUID());
        testData.exerciseTypeRequestDto1.getCategories().add(testData.categoryRequestDto1);
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(categoryRepository.findById(testData.categoryEntity1.getId())).thenReturn(Optional.empty());
        when(exerciseTypeMapper.mapFromDto(testData.exerciseTypeRequestDto1))
                .thenReturn(testData.exerciseTypeEntity1);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseTypeService.createExercise(testData.exerciseTypeRequestDto1, "user1"));

        assertEquals("Category with the ID " + testData.categoryRequestDto1.getId() + " not found.", exception.getMessage());
    }


    @Test
    void findAllForUser_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));
        when(exerciseTypeMapper.mapToDto(testData.exerciseTypeEntity1)).thenReturn(testData.exerciseTypeResponseDto1);
        when(exerciseTypeMapper.mapToDto(testData.exerciseTypeEntity2)).thenReturn(testData.exerciseTypeResponseDto2);
        when(exerciseTypeRepository.findByUserUsername("user1"))
                .thenReturn(Optional.of(List.of(testData.exerciseTypeEntity1, testData.exerciseTypeEntity2)));

        List<ExerciseTypeDto> result = exerciseTypeService.findAllForUser("user1");

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), testData.exerciseTypeResponseDto1);
        assertEquals(result.get(result.size() - 1), testData.exerciseTypeResponseDto2);

    }

    @Test
    void findAllForUser_UserNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                exerciseTypeService.findAllForUser("user1"));

        assertEquals("User with the username \"user1\" not found.", exception.getMessage());
    }

    @Test
    void deleteById_Success() {
        testData.user1.getExerciseTypes().add(testData.exerciseTypeEntity1);
        testData.exerciseTypeEntity1.setUser(testData.user1);
        UUID exerciseId = testData.exerciseTypeEntity1.getId();
        when(exerciseTypeRepository.findById(exerciseId)).thenReturn(Optional.of(testData.exerciseTypeEntity1));

        exerciseTypeService.deleteById(exerciseId);

        verify(exerciseTypeRepository, times(1)).deleteById(exerciseId);

    }

    @Test
    void deleteById_IdNotFound() {
        UUID id = UUID.randomUUID();
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseTypeService.deleteById(id));

        assertEquals("Exercise type with the ID " + id + " not found.", exception.getMessage());
    }

    @Test
    void updateById_Success() {

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        UUID id = testData.exerciseTypeEntity1.getId();

        ExerciseTypeEntity editedEntity = ExerciseTypeDataHelper.createExerciseTypeEntity("edited");
        editedEntity.setCategories(List.of(testData.categoryEntity3));
        editedEntity.setId(id);

        ExerciseTypeDto editedResponseDto = ExerciseTypeDataHelper.createExerciseTypeResponseDto("edited");
        editedResponseDto.setCategories(List.of(testData.categoryResponseDto3));
        editedResponseDto.setId(id);

        ExerciseTypeDto editedRequestDto = ExerciseTypeDataHelper.createExerciseTypeRequestDto("edited");
        editedRequestDto.setCategories(List.of(testData.categoryRequestDto3));
        editedRequestDto.setId(id);

        when(exerciseTypeRepository.findById(id)).thenReturn(Optional.ofNullable(testData.exerciseTypeEntity1));
        when(exerciseTypeMapper.mapFromDto(editedRequestDto)).thenReturn(editedEntity);
        when(exerciseTypeMapper.mapToDto(editedEntity)).thenReturn(editedResponseDto);
        when(exerciseTypeRepository.save(any(ExerciseTypeEntity.class))).thenReturn(editedEntity);


        ExerciseTypeDto result = exerciseTypeService.updateById(id, editedRequestDto, "user1");

        assertNotNull(result);
        assertEquals(testData.exerciseTypeEntity1.getId(), editedResponseDto.getId());
        assertEquals(editedResponseDto.getName(), "edited");
        assertEquals(editedResponseDto.getCategories(), List.of(testData.categoryResponseDto3));
    }

    @Test
    void updateById_IdNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testData.user1));

        UUID id = UUID.randomUUID();
        ExerciseTypeDto editedRequestDto = ExerciseTypeDataHelper.createExerciseTypeRequestDto("edited");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> exerciseTypeService.updateById(id, editedRequestDto, "user1"));

        assertEquals("Exercise type with the ID " + id + " not found.", exception.getMessage());
    }

}