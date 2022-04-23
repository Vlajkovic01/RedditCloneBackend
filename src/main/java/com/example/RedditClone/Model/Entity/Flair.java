package com.example.RedditClone.Model.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "flairs")
public class Flair {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "flair_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
