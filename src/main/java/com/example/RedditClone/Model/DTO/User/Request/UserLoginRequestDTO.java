package com.example.RedditClone.Model.DTO.User.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotBlank(message = "Username is mandatory")
    @Length(min = 3, max = 15, message = "Username must be between 3 and 15 characters.")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Length(min = 5, message = "Password must be longer than 5 characters.")
    private String password;
}
