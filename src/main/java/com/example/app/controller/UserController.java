package com.example.app.controller;

import com.example.app.db.entity.UserData;
import com.example.app.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
public class UserController {
    @Autowired
    UserDataService service;

    @PostMapping("/registration")
    public String registration(@RequestBody UserData user) {
        return service.newUser(user.getUsername(), user.getPassword());
    }
}
