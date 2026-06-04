package com.example.app.controller;

import com.example.app.entity.UserData;
import com.example.app.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/v1")
public class UserController {
    @Autowired
    UserDataService service;

    @GetMapping
    public String authorisation(@RequestBody UserData user) {
        return service.authorisation(user.getUsername(), user.getPassword());
    }
    @PostMapping
    public String registration(@RequestBody UserData user) {
        return service.newUser(user.getUsername(), user.getPassword());
    }
}
