package com.example.RedditClone.Model.DTO.Community.Response;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Moderator.Response.ModeratorGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Rule.Response.RuleGetByCommunityResponseDTO;
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
