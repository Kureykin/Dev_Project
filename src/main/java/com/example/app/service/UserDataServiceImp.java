package com.example.app.service;

import com.example.app.db.entity.UserData;
import com.example.app.untils.exception.InvalidPasswordException;
import com.example.app.untils.exception.UserAlreadyExistException;
import com.example.app.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDataServiceImp implements UserDataService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;

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

        String hash = encoder.encode(password);

        if(!repo.existsById(username)){
            UserData newUser = new UserData(username, hash);
            repo.save(newUser);

            return newUser.getUsername();
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
    }
    @Override
    public UserData findUserByName(String username) {
        return repo.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
