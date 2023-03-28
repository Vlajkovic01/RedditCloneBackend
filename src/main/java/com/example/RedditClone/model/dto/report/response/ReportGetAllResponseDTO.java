package com.example.RedditClone.model.dto.report.response;

import com.example.RedditClone.model.dto.comment.response.CommentGetForPostDTO;
import com.example.RedditClone.model.dto.community.response.CommunityGetForPostDTO;
import com.example.RedditClone.model.dto.post.response.PostGetForCommunityDTO;
import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
import com.example.RedditClone.model.enumeration.ReportReason;
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
    private PostGetForCommunityDTO post;
    private CommunityGetForPostDTO community;
}
