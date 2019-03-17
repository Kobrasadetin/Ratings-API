package com.majesticbyte.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<AppUser> members;

    @OneToMany
    private Set<AppUser> admins;

    private String name;
}
