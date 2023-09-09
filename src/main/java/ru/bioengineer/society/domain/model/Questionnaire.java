package ru.bioengineer.society.domain.model;

import java.time.LocalDate;
import java.time.Period;

public record Questionnaire(String id,
                            String firstName,
                            String secondName,
                            LocalDate birthdate,
                            String biography,
                            String city
) {

    public int age() {
        if (birthdate == null) {
            return -1;
        }
        LocalDate now = LocalDate.now();
        Period age = Period.between(birthdate, now);
        return age.getYears();
    }

}
