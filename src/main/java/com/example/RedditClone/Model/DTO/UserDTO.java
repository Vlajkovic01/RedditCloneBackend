package com.example.RedditClone.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;
    private String avatar;
    private LocalDate registrationDate;
    private String description;

    public UserDTO() {
    }

}
