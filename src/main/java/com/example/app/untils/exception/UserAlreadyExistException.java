package com.example.app.untils.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("This User already exist");
    }
}
