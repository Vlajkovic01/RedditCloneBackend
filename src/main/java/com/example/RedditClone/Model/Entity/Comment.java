package com.example.RedditClone.Model.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String text;
    private LocalDate timestamp;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
    private Comment parent;

    @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "parent")
    private Set<Comment> children = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;
}
