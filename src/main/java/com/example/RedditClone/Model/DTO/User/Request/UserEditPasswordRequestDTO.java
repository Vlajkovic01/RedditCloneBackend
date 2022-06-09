package com.example.RedditClone.Model.DTO.User.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditPasswordRequestDTO {

    private String currentPassword;
    private String newPassword;
}
