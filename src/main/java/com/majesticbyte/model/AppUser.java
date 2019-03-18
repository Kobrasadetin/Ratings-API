package com.majesticbyte.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class AppUser {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @JsonIgnore
    private String password;

    @NotNull
    private String email;

    private String role;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "admins", fetch = FetchType.LAZY)
    private Set<UserGroup> adminInGroups = new HashSet<>();


    public static AppUser adminWithProperties(String username, String password) {
        return AppUser.withProperties(
                null,
                username,
                password,
                ROLE_ADMIN,
                "email@example.com");
    }

    public static AppUser userWithProperties(String username, String password) {
        return AppUser.withProperties(
                null,
                username,
                password,
                ROLE_USER,
                "email@example.com");
    }

    public static AppUser withProperties(String username, String password, String role) {
        return AppUser.withProperties(
                null,
                username,
                password,
                role,
                "email@example.com");
    }

    public static AppUser withProperties(Long id, String username, String password, String role, String email) {
        AppUser user = new AppUser();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        return user;
    }
}
