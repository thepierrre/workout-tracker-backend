package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.LoginDto;
import com.example.gymapp.domain.dto.RegisterDto;

public class AuthDataHelper {

    public static LoginDto createLoginDto(String username, String password) {

        return LoginDto.builder()
                .username(username)
                .password(password)
                .build();
    }

    public static RegisterDto createRegisterDto(String username, String email, String password) {

        return RegisterDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

}
