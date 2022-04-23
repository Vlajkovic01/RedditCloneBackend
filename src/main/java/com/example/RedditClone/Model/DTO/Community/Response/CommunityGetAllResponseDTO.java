package com.example.RedditClone.Model.DTO.Community.Response;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Flair;
import com.example.RedditClone.Model.Entity.Moderator;
import com.example.RedditClone.Model.Entity.Post;
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
    private Set<String> rules = new HashSet<String>();
    private Set<UserGetAllResponseDTO> moderators = new HashSet<UserGetAllResponseDTO>();
    private Set<PostGetAllResponseDTO> posts = new HashSet<PostGetAllResponseDTO>();
    private Set<FlairGetAllResponseDTO> flairs = new HashSet<FlairGetAllResponseDTO>();
}
