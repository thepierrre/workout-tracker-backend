package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;

    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
