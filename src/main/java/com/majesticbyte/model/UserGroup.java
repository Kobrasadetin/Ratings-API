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

    @ManyToMany(mappedBy = "groups")
    private Set<AppUser> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<AppUser> admins = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "match_record_id")
    private Set<MatchRecord> matchRecords = new HashSet<>();

    @NotNull
    private String name;
}
