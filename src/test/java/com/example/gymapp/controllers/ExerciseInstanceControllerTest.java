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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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
                .andExpect(jsonPath("$.workingSets[0].weight", is(15.0)));
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
        WorkingSetDto input = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 15);
        String jsonInput = objectMapper.writeValueAsString(input);

        testData.exerciseInstanceResponseDto1.setWorkingSets(List.of(input));

        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        testData.exerciseInstanceResponseDto1.setWorkingSets(List.of(input));

        when(exerciseInstanceService.updateWorkingSetById(any(), any(), any()))
                .thenReturn(testData.exerciseInstanceResponseDto1);

        mvc.perform(patch("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk());

    }

    @Test
    void updateWorkingSetById_ExerciseIdNotFound() throws Exception {
        WorkingSetDto input = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 15);
        String jsonInput = objectMapper.writeValueAsString(input);

        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceService.updateWorkingSetById(any(), any(), any()))
                .thenThrow(new EntityNotFoundException(
                        String.format("Exercise instance with the ID %s not found.", exerciseId.toString())));

        mvc.perform(patch("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Exercise instance with the ID %s not found.", exerciseId.toString())));
    }

    @Test
    void updateWorkingSetById_SetIdNotFound() throws Exception {
        WorkingSetDto input = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 15);
        String jsonInput = objectMapper.writeValueAsString(input);

        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceService.updateWorkingSetById(any(), any(), any()))
                .thenThrow(new EntityNotFoundException(
                        String.format("Set with the ID %s not found.", setId)));

        mvc.perform(patch("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Set with the ID %s not found.", setId)));
    }

    @Test
    void deleteWorkingSetById_Success() throws Exception {
        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        testData.exerciseInstanceResponseDto1.setWorkingSets(List.of());

        when(exerciseInstanceService.deleteWorkingSetById(any(), any()))
                .thenReturn(testData.exerciseInstanceResponseDto1);

        mvc.perform(delete("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseTypeName", containsString("exerciseType1")))
                .andExpect(jsonPath("$.workingSets", is(List.of())));
    }

    @Test
    void deleteWorkingSetById_ExerciseIdNotFound() throws Exception {
        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceService.deleteWorkingSetById(any(), any()))
                .thenThrow(new EntityNotFoundException(
                        String.format("Exercise instance with the ID %s not found.", exerciseId)));

        mvc.perform(delete("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Exercise instance with the ID %s not found.", exerciseId.toString())));
    }

    @Test
    void deleteWorkingSetById_SetIdNotFound() throws Exception {
        UUID exerciseId = UUID.randomUUID();
        UUID setId = UUID.randomUUID();

        when(exerciseInstanceService.deleteWorkingSetById(any(), any()))
                .thenThrow(new EntityNotFoundException(
                        String.format("Set with the ID %s not found.", setId)));

        mvc.perform(delete("/api/exercise-instances/" + exerciseId + "/sets/" + setId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Set with the ID %s not found.", setId)));
    }

    @Test
    void deleteExerciseInstanceById_Success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing()
                .when(exerciseInstanceService).deleteById(any());

        mvc.perform(delete("/api/exercise-instances/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void deleteExerciseInstanceById_ExerciseIdNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new EntityNotFoundException(
                String.format("Exercise instance with the ID %s not found.", id)))
                .when(exerciseInstanceService).deleteById(any());

        mvc.perform(delete("/api/exercise-instances/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        String.format("Exercise instance with the ID %s not found.", id)));
    }
}