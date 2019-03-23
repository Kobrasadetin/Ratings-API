package com.majesticbyte.model;

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
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<AppUser> members = new HashSet<>();

    @ManyToOne
    private AppUser creator;

    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "group_admins",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<AppUser> admins = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "match_record_id")
    private Set<MatchRecord> matchRecords = new HashSet<>();

    @NotNull
    private String name;
}
