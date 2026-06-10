package com.example.app.until.exception;

public class NoUserException extends RuntimeException {
    public NoUserException() {
        super("This User does not exist");
    }
}
