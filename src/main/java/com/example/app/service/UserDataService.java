package com.example.app.service;

import com.example.app.db.entity.UserData;

public interface UserDataService {
    public String newUser(String username, String password);
    public UserData findUserByName(String username);
}
