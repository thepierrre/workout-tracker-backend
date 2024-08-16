package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Email address cannot be empty.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
