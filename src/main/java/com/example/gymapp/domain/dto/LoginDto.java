package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    private String password;
}

