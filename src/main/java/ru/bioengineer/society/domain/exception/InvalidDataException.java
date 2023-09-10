package ru.bioengineer.society.domain.exception;

public class InvalidDataException extends SocietyException {

    public InvalidDataException(String message) {
        super(message, -1);
    }

    public InvalidDataException(String message, int code) {
        super(message, code);
    }

}
