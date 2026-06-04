package com.example.app.service;

import com.example.app.entity.UserData;
import com.example.app.exception.InvalidPasswordException;
import com.example.app.exception.NoUserException;
import com.example.app.exception.UserAlreadyExistException;
import com.example.app.exception.WrongPasswordException;
import com.example.app.repository.UserRepository;
import com.example.app.token.JWTService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Objects;

public class UserDataServiceImp implements UserDataService {

    @Autowired
    private UserRepository repo;

    private boolean hasChars(String password, char[] chars) {
        for (char c: chars) {
            if(password.contains("" + c)){
                return true;
            }
        }
        return false;
    }

    private boolean isPasswordValid(String password){
        char[] bigLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] smallLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] numbers = "1234567890".toCharArray();

        return hasChars(password, bigLetters) && hasChars(password, smallLetters) && hasChars(password, numbers) && password.length() >= 8;

    }

    @Override
    public String newUser(String username, String password) throws InvalidPasswordException {

        if(!isPasswordValid(password)) {
            throw new InvalidPasswordException();
        }

        if(!repo.existsById(username)){
            UserData newUser = new UserData(username, password);
            repo.save(newUser);

            return new JWTService().compile(newUser.getUsername());
        }

        throw new UserAlreadyExistException();
    }

    @Override
    public String authorisation(String username, String password) {

        try{
            UserData data = repo.findById(username).get();

            if(Objects.equals(data.getPassword(), password)) {
                return new JWTService().compile(data.getUsername());
            }
        } catch (NoSuchElementException e) {
            throw new NoUserException();
        }

        throw new WrongPasswordException();
    }
}
