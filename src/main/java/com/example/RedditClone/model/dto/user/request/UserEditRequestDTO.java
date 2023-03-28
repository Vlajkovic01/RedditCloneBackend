package com.example.RedditClone.model.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditRequestDTO {

    private String avatar;
    private String description;
    private String displayName;
}
