package com.example.RedditClone.Model.DTO.Moderator.Request;

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
