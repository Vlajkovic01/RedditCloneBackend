package com.example.RedditClone.Model.DTO.User.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserEditRequestDTO {

    private String currentPassword;
    private String newPassword;
    private String avatar;
    private String description;
    private String displayName;
}
