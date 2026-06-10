package com.example.app.until.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("This User already exist");
    }
}
