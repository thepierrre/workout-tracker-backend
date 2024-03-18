package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetDto {

    private Short id;

    private ExerciseInstanceDto exerciseInstance;

    private Short reps;

    private Short weight;

}
