package com.example.RedditClone.Model.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reaction {

    private Integer id;
    private ReactionType type;
    private LocalDate timestamp;
    private User user;
    private Post post;
    private Comment comment;
}
