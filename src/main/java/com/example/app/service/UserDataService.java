package com.example.app.service;

import com.example.app.entity.UserData;

public interface UserDataService {
    public String newUser(String username, String password);
    public String authorisation(String username, String password);
}
