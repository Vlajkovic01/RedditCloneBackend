package com.example.RedditClone.model.dto.post.response;

import com.example.RedditClone.model.dto.comment.response.CommentGetForPostDTO;
import com.example.RedditClone.model.dto.community.response.CommunityGetForPostDTO;
import com.example.RedditClone.model.dto.flair.response.FlairGetAllResponseDTO;
import com.example.RedditClone.model.dto.reaction.response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
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
public class PostGetAllResponseDTO {
    private Integer id;
    private String title;
    private String text;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate creationDate;
    private String imagePath;
    private UserGetAllResponseDTO user;
    private FlairGetAllResponseDTO flair;
    private CommunityGetForPostDTO community;
    private Set<ReactionForPostAndCommentDTO> reactions = new HashSet<>();
    private Set<CommentGetForPostDTO> comments = new HashSet<>();

}
