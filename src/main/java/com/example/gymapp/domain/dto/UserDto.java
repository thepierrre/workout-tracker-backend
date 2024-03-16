package com.example.gymapp.domain.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "The username must not be empty.")
    private String name;

    @NotBlank(message = "The password must not be empty.")
    private String password;

    private List<TrainingTypeDto> trainingTypes;
}
