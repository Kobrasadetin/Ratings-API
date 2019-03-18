package com.majesticbyte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class MatchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean resolved;

    @ManyToOne
    private UserGroup group;

    @ManyToMany
    private Set<AppUser> players;

    private Date date;

}
