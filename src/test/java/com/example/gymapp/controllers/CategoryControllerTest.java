package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    CategoryService categoryService;

    private static CategoryEntity category1;

    private static UserEntity user1;

    @BeforeAll
    static void setUpEntities() {

        UserEntity user1 = UserEntity.builder()
                .username("user1")
                .email("user1@example.com")
                .password("password1")
                .build();

        CategoryEntity category1 = CategoryEntity.builder()
                .name("triceps")
                .build();
    }

    @BeforeEach
    void setUp() {

        when(userRepository.existsByUsername("user1")).thenReturn(true);
        when(userRepository.save(user1)).thenReturn(user1);
        userRepository.save(user1);

        when(categoryRepository.existsByName("triceps")).thenReturn(true);
        when(categoryRepository.save(category1)).thenReturn(category1);
        categoryRepository.save(category1);
    }

    @ParameterizedTest
    @MethodSource("provideCreateCategoryPayloadAndExpectedResults")
    void testCreateCategory(String testCase, String input, String message, int errorCode) throws Exception {

        CategoryDto categoryDto = objectMapper.readValue(input, CategoryDto.class);
        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setName(categoryDto.getName());

        if (errorCode == 201) {
            when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(expectedCategoryDto);
        } else if (errorCode == 409) {
            when(categoryService.createCategory(any(CategoryDto.class)))
                    .thenThrow(new IllegalArgumentException("Category with the name " + categoryDto.getName() + " already exists."));
        } else if (errorCode == 400) {
            // Handle any additional logic for bad requests here if needed
        }

        mvc.perform(post("/api/categories").with(user("user1"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(input))
                .andExpect(status().is(errorCode))
                .andExpect(content().string("{\"id\":null,\"name\":\"front deltoids\",\"exerciseTypes\":null}"))
                .andDo(result -> System.out.println(testCase));


    }

    @Test
    void testDeleteAll() throws Exception {}

    @ParameterizedTest
    @MethodSource("provideDeleteByIdPayloadAndExpectedResults")
    void testDeleteById() throws Exception {}

    @Test
    void testFindAll() throws Exception {}


    private static Stream<Arguments> provideCreateCategoryPayloadAndExpectedResults() {

        return Stream.of(
                Arguments.of(
                    "OK",
                        """
                            {
                                "name": "front deltoids"
                            }
                        """,
                        "",
                        201
                )
        );
    }

    private static Stream<Arguments> provideDeleteByIdPayloadAndExpectedResults() {

        return Stream.of(
                Arguments.of(

                )
        );
    }

}