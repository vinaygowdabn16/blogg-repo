package com.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String name;

//    @ManyToMany(mappedBy="roles" )// these line of codes create problem like handler dispatch failed
//    private Set<User> users = new HashSet<>();
}
