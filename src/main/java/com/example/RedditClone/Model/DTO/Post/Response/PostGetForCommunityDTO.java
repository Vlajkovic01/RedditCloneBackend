package com.example.RedditClone.Model.DTO.Post.Response;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostGetForCommunityDTO {

    private Integer id;
    private String title;
    private String text;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate creationDate;
    private String imagePath;
    private UserGetAllResponseDTO user;
    private FlairGetAllResponseDTO flair;
    private Set<ReactionForPostAndCommentDTO> reactions = new HashSet<>();
}
