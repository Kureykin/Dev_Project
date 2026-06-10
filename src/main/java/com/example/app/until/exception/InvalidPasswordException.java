package com.example.app.until.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password. Your password must be at least 8 characters long and include numbers, uppercase letters, and lowercase letters. ");
    }
}
