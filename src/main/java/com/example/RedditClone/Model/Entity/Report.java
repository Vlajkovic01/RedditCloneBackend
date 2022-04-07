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
public class Report {
    private Integer id;
    private LocalDate timestamp;
    private User byUser;
    private Boolean accepted;
    private Comment comment;
    private Post post;
}
