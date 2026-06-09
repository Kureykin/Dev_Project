package com.example.app.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "`Users`")
public class UserData {
    @Id
    @Column(name = "`Username`")
    private String username;
    @Column(name = "`Password`", nullable = false)
    private String password;
    @OneToMany(mappedBy = "username")
    Set<UrlData> data;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserData data = (UserData) object;
        return Objects.equals(username, data.username) && Objects.equals(password, data.password);
    }

}
