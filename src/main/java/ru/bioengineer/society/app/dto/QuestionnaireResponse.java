package ru.bioengineer.society.app.dto;

import java.time.LocalDate;

public record QuestionnaireResponse(
        String id,
        String firstName,
        String secondName,
        int age,
        LocalDate birthdate,
        String biography,
        String city) {
}
