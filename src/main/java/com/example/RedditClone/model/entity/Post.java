package com.example.RedditClone.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "posts")
@Where(clause = "(select 1 from communities c where c.community_id = community_id and c.is_suspended=false)" +
                "and (not exists(select 1 from reports r where post_id = r.post_id and r.accepted = 1))")
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;
    private String pdfFileName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flair_id", referencedColumnName = "flair_id")
    private Flair flair;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(cascade = {ALL, REFRESH}, fetch = EAGER, orphanRemoval = true, mappedBy = "post")
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(cascade = {ALL, REFRESH}, fetch = EAGER, orphanRemoval = true, mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();
}
