package ru.bioengineer.society.domain.model;

public record User(String id,
                   String passwordHash) {

    public String getRole() {
        return "USER";
    }

}
