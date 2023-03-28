package com.example.RedditClone.model.dto.banned.request;

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
