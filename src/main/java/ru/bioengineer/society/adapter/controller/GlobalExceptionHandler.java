package ru.bioengineer.society.adapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bioengineer.society.adapter.utils.WebUtils;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e) {
        return WebUtils.createErrorResponse(e, 400);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return WebUtils.createErrorResponse(e, 404);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<?> handleInternalException(InternalException e) {
        return WebUtils.createErrorResponse(e, 500);
    }

    @ExceptionHandler(TemporaryException.class)
    public ResponseEntity<?> handleTemporaryException(TemporaryException e) {
        return WebUtils.createErrorResponse(e, 503);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return WebUtils.createErrorResponse(e, 500);
    }

}
