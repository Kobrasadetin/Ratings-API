package com.majesticbyte.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String role;

    public static AppUser withProperties(Long id, String username, String password, String role) {
        AppUser user = new AppUser();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
