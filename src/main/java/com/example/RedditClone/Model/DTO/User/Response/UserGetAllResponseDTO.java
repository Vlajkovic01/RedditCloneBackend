package com.example.RedditClone.Model.DTO.User.Response;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserGetAllResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private String description;
    private LocalDate registrationDate;
    private String displayName;
}
