package com.example.RedditClone.Model.DTO.Report.Response;

import com.example.RedditClone.Model.DTO.Comment.Response.CommentGetForPostDTO;
import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetForPostDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.Enum.ReportReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReportGetAllResponseDTO {

    private Integer id;
    private ReportReason reason;
    private LocalDate timestamp;
    private Boolean accepted;
    private UserGetAllResponseDTO byUser;
    private CommentGetForPostDTO comment;
    private PostGetAllResponseDTO post;
    private CommunityGetForPostDTO community;
}
