package com.example.RedditClone.Model.DTO.Banned.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BannedCreateDTO {
    private Integer userId;
    private Integer communityId;
}
