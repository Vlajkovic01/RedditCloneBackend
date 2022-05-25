package com.example.RedditClone.Model.DTO.Post.Response;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetForPostDTO;
import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PostGetAllResponseDTO {
    private Integer id;
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;
    private UserGetAllResponseDTO user;
    private FlairGetAllResponseDTO flair;
    private CommunityGetForPostDTO community;
}
