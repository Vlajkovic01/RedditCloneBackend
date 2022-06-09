package com.example.RedditClone.Model.DTO.Reaction.Response;

import com.example.RedditClone.Model.Enum.ReactionType;
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
