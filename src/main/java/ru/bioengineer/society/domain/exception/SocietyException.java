package ru.bioengineer.society.domain.exception;

public class SocietyException extends RuntimeException {

    private final int code;

    public SocietyException(String message) {
        super(message);
        this.code = -1;
    }

    public SocietyException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
