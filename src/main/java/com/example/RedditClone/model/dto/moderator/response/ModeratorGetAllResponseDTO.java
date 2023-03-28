package com.example.RedditClone.model.dto.moderator.response;

import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModeratorGetAllResponseDTO {
    private Integer id;
    private UserGetAllResponseDTO user;
}
