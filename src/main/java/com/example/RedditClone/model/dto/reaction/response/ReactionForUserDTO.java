package com.example.RedditClone.model.dto.reaction.response;

import com.example.RedditClone.model.enumeration.ReactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReactionForUserDTO {

    private Integer id;
    private ReactionType type;
    private LocalDate timestamp;
}
