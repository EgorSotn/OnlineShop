package ru.sotn.securityserver.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(String.format("Not Found: %s", message));
    }
}
