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
    private String confirmNewPassword;
    private String avatar;
    private String description;

    @NotBlank(message = "Display name is mandatory")
    @Length(min = 3, max = 15, message = "Display name must be between 3 and 15 characters.")
    private String displayName;
}
