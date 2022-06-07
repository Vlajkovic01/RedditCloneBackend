package com.example.RedditClone.Model.DTO.Reaction.Request;

import com.example.RedditClone.Model.Enum.ReactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactionCreateRequestDTO {
    private ReactionType type;
    private Integer postId;
    private Integer commentId;
}
