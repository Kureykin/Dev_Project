package com.example.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserData {
    @Id
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
