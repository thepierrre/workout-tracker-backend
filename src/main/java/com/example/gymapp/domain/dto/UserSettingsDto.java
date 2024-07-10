package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingsDto {

    private UUID id;

    @JsonIgnore
    private UserDto user;

    @Transient
    private String username;

    @Min(value = 1, message = "The minimum value is 1.")
    @Max(value = 100, message = "The maximum value is 100.")
    private short changeThreshold;

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    public void setUsername(String username) {
        if (user == null) {
            user = new UserDto();
        }
        this.username = username;
    }


}
