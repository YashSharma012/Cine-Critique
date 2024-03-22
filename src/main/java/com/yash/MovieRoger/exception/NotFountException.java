package com.yash.MovieRoger.exception;

public class NotFountException extends RuntimeException {
    private final String message;

    public NotFountException(String message) {
        super(message);
        this.message = message;
    }
}
