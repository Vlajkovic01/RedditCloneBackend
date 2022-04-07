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
public class Post {
    private Integer id;
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;
    private User user;
    private Flair flair;
}
