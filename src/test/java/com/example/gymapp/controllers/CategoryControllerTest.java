package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CategoryController categoryController;

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
                .id(UUID.fromString("6a8f0e74-3c6b-4c97-8f70-b0742cb1c3ec"))
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

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideCreateCategoryPayloadAndExpectedResults")
    void testCreateCategory(String testCase, String input, String message, int errorCode, boolean isSuccess) throws Exception {

        CategoryDto categoryDto = objectMapper.readValue(input, CategoryDto.class);
        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setName(categoryDto.getName());
        expectedCategoryDto.setId(UUID.randomUUID());

        if (isSuccess) {
            when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(expectedCategoryDto);
        } else if (errorCode == 409) {
            when(categoryService.createCategory(any(CategoryDto.class)))
                    .thenThrow(new ConflictException("Category with the name '" + categoryDto.getName() + "' already exists."));
        }

        var resultActions = mvc.perform(post("/api/categories").with(user("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().is(errorCode));

        if (isSuccess) {
            resultActions.andExpect(jsonPath("$.name").value(expectedCategoryDto.getName()))
                    .andExpect(jsonPath("$.id").value(expectedCategoryDto.getId().toString()));
        } else {
            resultActions.andExpect(content().string(containsString(message)));
        }

        resultActions.andDo(result -> System.out.println(testCase));

    }

    @Test
    void testDeleteAll() throws Exception {

        mvc.perform(delete("/api/categories").with(user("user1"))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

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
                        201,
                        true
                ),
                Arguments.of(
                        "category already exists",
                        """
                            {
                                "name": "triceps"
                            }
                        """,
                        "Category with the name 'triceps' already exists.",
                        409,
                        false
                ),
                Arguments.of(
                        "empty category name",
                        """
                            {
                                "name": ""
                            }
                        """,
                        "{\"name\":\"Category name cannot be empty.\"}",
                        400,
                        false
                )
        );
    }

    private static Stream<Arguments> provideDeleteByIdPayloadAndExpectedResults() {

        return Stream.of(
                Arguments.of(
                        "OK",
                        "6a8f0e74-3c6b-4c97-8f70-b0742cb1c3ec",
                        204,
                        true
                ),
                Arguments.of(
                        "no category id specified",
                        "",
                        204,
                        false
                ),
                Arguments.of(
                        "incorrect category id",
                        "",
                        204,
                        false
                )
        );
    }

}