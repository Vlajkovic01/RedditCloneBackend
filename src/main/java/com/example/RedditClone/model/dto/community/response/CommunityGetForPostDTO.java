package com.example.RedditClone.model.dto.community.response;

import com.example.RedditClone.model.dto.flair.response.FlairGetAllResponseDTO;
import com.example.RedditClone.model.dto.moderator.response.ModeratorGetAllResponseDTO;
import com.example.RedditClone.model.dto.rule.response.RuleGetByCommunityResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class CommunityGetForPostDTO {

    private Integer id;
    private String name;
    private String description;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate creationDate;
    private Boolean isSuspended;
    private String suspendedReason;
    private Set<RuleGetByCommunityResponseDTO> rules = new HashSet<>();
    private Set<ModeratorGetAllResponseDTO> moderators = new HashSet<>();
    private Set<FlairGetAllResponseDTO> flairs = new HashSet<>();
}
