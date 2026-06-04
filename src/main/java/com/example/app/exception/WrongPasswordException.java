package com.example.app.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Wrong Password");
    }
}
