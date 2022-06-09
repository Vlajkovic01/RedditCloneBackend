package com.example.RedditClone.Model.DTO.User.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class UserForMyProfileDTO {

    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private String description;
    private LocalDate registrationDate;
    private String displayName;
    private Integer totalKarma;
}
