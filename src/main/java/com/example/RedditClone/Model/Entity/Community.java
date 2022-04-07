package com.example.RedditClone.Model.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Community {
    private Integer id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private List<String> rules;
    private Boolean isSuspended;
    private String suspendedReason;
    private Set<Moderator> moderators = new HashSet<Moderator>();
    private Set<Post> posts = new HashSet<Post>();
    private Set<Flair> flairs = new HashSet<Flair>();
}
