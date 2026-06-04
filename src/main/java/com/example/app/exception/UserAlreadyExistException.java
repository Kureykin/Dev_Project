package com.example.app.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("This User already exist");
    }
}
