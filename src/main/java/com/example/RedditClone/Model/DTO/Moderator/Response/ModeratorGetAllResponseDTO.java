package com.example.RedditClone.Model.DTO.Moderator.Response;

import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModeratorGetAllResponseDTO {
    private UserGetAllResponseDTO user;
}
