package com.example.RedditClone.model.dto.moderator.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModeratorDeleteFromCommunityDTO {
    Integer communityId;
    Integer moderatorId;
}
