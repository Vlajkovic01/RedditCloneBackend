package com.example.RedditClone.Model.DTO.Community.Response;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Moderator.Response.ModeratorGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Moderator;
import com.example.RedditClone.Model.Entity.Rule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CommunityGetAllResponseDTO {

    private String name;
    private String description;
    private LocalDate creationDate;
    private Boolean isSuspended;
    private String suspendedReason;
    private Set<Rule> rules = new HashSet<>();
    private Set<ModeratorGetAllResponseDTO> moderators = new HashSet<>();
    private Set<PostGetAllResponseDTO> posts = new HashSet<>();
    private Set<FlairGetAllResponseDTO> flairs = new HashSet<>();
}
