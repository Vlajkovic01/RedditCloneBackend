package com.example.RedditClone.Model.DTO.User.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserGetAllRequestDTO {

    private String username;
    private String email;
    private String avatar;
    private String description;
    private LocalDate registrationDate;
//    private String user_type; //TODO implement to return user type from db
}
