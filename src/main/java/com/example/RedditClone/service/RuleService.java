package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.rule.response.RuleGetByCommunityResponseDTO;
import com.example.RedditClone.model.entity.Community;

import java.util.List;

public interface RuleService {

    List<RuleGetByCommunityResponseDTO> findAllByCommunity(Community community);
}
