package com.example.RedditClone.Model.DTO.Comment.Response;

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
public class CommentGetForPostDTO{

    private Integer id;
    private String text;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate timestamp;
    private Boolean isDeleted;
    private Integer parentCommentId;
    private Set<CommentGetForPostDTO> children = new HashSet<>();
    private UserGetAllResponseDTO user;
    private Set<ReactionForPostAndCommentDTO> reactions = new HashSet<>();
}
