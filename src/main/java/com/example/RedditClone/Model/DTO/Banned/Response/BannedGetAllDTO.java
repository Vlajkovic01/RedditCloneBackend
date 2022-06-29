package com.example.RedditClone.Model.DTO.Banned.Response;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class BannedGetAllDTO {
    private Integer id;
    private LocalDate timestamp;
    private UserGetAllResponseDTO by;
    private UserGetAllResponseDTO user;
    private CommunityGetAllResponseDTO community;
}
