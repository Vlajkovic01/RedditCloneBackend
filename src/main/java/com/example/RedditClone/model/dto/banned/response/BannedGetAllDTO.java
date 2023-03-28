package com.example.RedditClone.model.dto.banned.response;

import com.example.RedditClone.model.dto.community.response.CommunityGetAllResponseDTO;
import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
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
