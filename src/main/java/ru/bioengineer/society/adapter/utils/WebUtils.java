package ru.bioengineer.society.adapter.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.bioengineer.society.app.dto.ErrorResponse;
import ru.bioengineer.society.domain.exception.SocietyException;

import java.util.UUID;

public final class WebUtils {

    public static <T extends SocietyException> ResponseEntity<ErrorResponse> createErrorResponse(T throwable, int responseCode) {
        ErrorResponse errorResponse = new ErrorResponse(throwable.getMessage(), UUID.randomUUID().toString(), throwable.getCode());
        return ResponseEntity.status(HttpStatusCode.valueOf(responseCode)).body(errorResponse);
    }

    private WebUtils() {

    }
}
