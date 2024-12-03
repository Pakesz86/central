package com.pakesz.central.exception;

public class ConflictingStateException extends RuntimeException {
    public ConflictingStateException(String message) {
        super(message);
    }
}
