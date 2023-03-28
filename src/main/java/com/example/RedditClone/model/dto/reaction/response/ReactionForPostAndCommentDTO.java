package com.example.RedditClone.model.dto.reaction.response;

import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
import com.example.RedditClone.model.enumeration.ReactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReactionForPostAndCommentDTO {

    private Integer id;
    private ReactionType type;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate timestamp;
    private UserGetAllResponseDTO user;
}
