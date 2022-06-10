package com.example.RedditClone.Model.Entity;

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
@NoArgsConstructor
@Entity
@Table(name = "communities")
@Where(clause = "is_suspended=false")
public class Community {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "community_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private String description;
    private LocalDate creationDate;
    private Boolean isSuspended;

    @Column()
    private String suspendedReason;

    @OneToMany(cascade = {ALL, REFRESH}, fetch = EAGER, orphanRemoval = true, mappedBy = "community")
    private Set<Moderator> moderators = new HashSet<>();

    @OneToMany(cascade = {ALL}, fetch = EAGER, mappedBy = "community")
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = EAGER)
    private Set<Flair> flairs = new HashSet<>();

    @OneToMany(cascade = {ALL, REFRESH}, fetch = EAGER, orphanRemoval = true, mappedBy = "community")
    private Set<Rule> rules = new HashSet<>();

    public void removePost(Post post) {
        posts.remove(post);
        post.setCommunity(null);
    }
    public void removeModerator(Moderator moderator) {
        moderator.setCommunity(null);
        moderators.remove(moderator);
    }
}
