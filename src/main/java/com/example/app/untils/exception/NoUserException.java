package com.example.app.untils.exception;

public class NoUserException extends RuntimeException {
    public NoUserException() {
        super("This User does not exist");
    }
}
