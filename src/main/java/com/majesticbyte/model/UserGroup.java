package com.majesticbyte.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<AppUser> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<AppUser> admins = new HashSet<>();

    private String name;
}
