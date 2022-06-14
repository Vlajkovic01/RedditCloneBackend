package com.example.RedditClone.Model.DTO.User.Response;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate registrationDate;
    private String displayName;
}
