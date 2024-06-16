package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.helpers.WorkingSetDataHelper;
import com.example.gymapp.services.ExerciseInstanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
class ExerciseInstanceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ExerciseInstanceService exerciseInstanceService;

    TestDataInitializer.TestData testData;

    @BeforeEach
    void setUp() {
        testData = TestDataInitializer.initializeTestData();
    }

    @Test
    void createWorkingSetForExercise_Success() throws Exception {
        WorkingSetDto input = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 15);
        String jsonInput = objectMapper.writeValueAsString(input);

        testData.exerciseInstanceResponseDto1.setWorkingSets(List.of(input));

        when(exerciseInstanceService.createWorkingSetforExercise(any(), any()))
                .thenReturn(testData.exerciseInstanceResponseDto1);

        mvc.perform(post("/api/exercise-instances/" + UUID.randomUUID() + "/sets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.exerciseTypeName", containsString("exerciseType1")))
                .andExpect(jsonPath("$.workingSets[0].reps", is(10)))
                .andExpect(jsonPath("$.workingSets[0].weight", is(15)));
    }

    @Test
    void createWorkingSetForExercise_ExerciseIdNotFound() throws Exception {
        WorkingSetDto input = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 15);
        String jsonInput = objectMapper.writeValueAsString(input);

        UUID id = UUID.randomUUID();

        when(exerciseInstanceService.createWorkingSetforExercise(any(), any()))
                .thenThrow(new EntityNotFoundException(
                        String.format("Exercise instance with the ID %s not found.", id.toString())));

        mvc.perform(post("/api/exercise-instances/" + id + "/sets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Exercise instance with the ID %s not found.", id.toString())));
    }

    @Test
    void updateWorkingSetById_Success() throws Exception {
    }

    @Test
    void updateWorkingSetById_ExerciseIdNotFound() throws Exception {
    }

    @Test
    void updateWorkingSetById_SetIdNotFound() throws Exception {
    }

    @Test
    void deleteWorkingSetById_Success() throws Exception {
    }

    @Test
    void deleteWorkingSetById_ExerciseIdNotFound() throws Exception {
    }

    @Test
    void deleteWorkingSetById_SetIdNotFound() throws Exception {
    }

    @Test
    void deleteById_Success() throws Exception {
    }

    @Test
    void deleteById_ExerciseIdNotFound() throws Exception {
    }
}