package com.example.RedditClone.model.entity;

import com.example.RedditClone.model.enumeration.ReactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reaction_id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType type;

    @Column(nullable = false)
    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;
}
