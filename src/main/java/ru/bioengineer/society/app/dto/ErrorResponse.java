package ru.bioengineer.society.app.dto;

public record ErrorResponse(String message, String requestId, int code) {
}
