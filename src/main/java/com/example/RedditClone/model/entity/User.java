package com.example.RedditClone.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = STRING)
@DiscriminatorValue("USER")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;
    private String password;
    private String email;
    @Column
    private String avatar;
    @Column(nullable = false)
    private LocalDate registrationDate;
    @Column
    private String description;
    private String displayName;

//    @OneToMany(cascade = {ALL}, fetch = EAGER, mappedBy = "user")
//    private Set<Reaction> reactions = new HashSet<>();
}
