package ru.bioengineer.society.app.dto;

import java.time.LocalDate;

public record UserRegistrationRequest(
        String firstName,
        String secondName,
        LocalDate birthdate,
        String biography,
        String city,
        String password) {
}
