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
public class Comment {
    private Integer id;
    private String text;
    private LocalDate timestamp;
    private Boolean isDeleted;
    private Comment parent;
    private User user;
    private Post post;
}
