package com.example.RedditClone.model.dto.reaction.request;

import com.example.RedditClone.model.enumeration.ReactionType;
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
