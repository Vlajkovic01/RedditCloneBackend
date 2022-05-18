package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Rule.Response.RuleGetByCommunityResponseDTO;
import com.example.RedditClone.Model.Entity.Community;

import java.util.List;

public interface RuleService {

    List<RuleGetByCommunityResponseDTO> findAllByCommunity(Community community);
}
