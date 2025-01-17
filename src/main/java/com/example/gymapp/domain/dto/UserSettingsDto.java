package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingsDto {

    private UUID id;

    @DecimalMin(value = "0.1", message = "The minimum value is 0.1.")
    @Max(value = 200, message = "The maximum value is 200.")
    private Double changeThreshold;

    private String weightUnit;

}
