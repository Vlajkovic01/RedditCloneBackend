package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.community.request.CommunityCreateRequestDTO;
import com.example.RedditClone.model.dto.community.request.CommunityEditRequestDTO;
import com.example.RedditClone.model.dto.community.request.CommunitySuspendRequestDTO;
import com.example.RedditClone.model.entity.Community;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface CommunityService {
    List<Community> findAll();
    List<Community> find12RandomCommunities();
    Community findCommunityById(Integer id);
    Community createCommunity(CommunityCreateRequestDTO communityCreateRequestDTO, Authentication authentication);
    Community editCommunity(CommunityEditRequestDTO communityEditRequestDTO, Community community);

    Community save(Community community);

    void suspendCommunity(Community community, CommunitySuspendRequestDTO communitySuspendRequestDTO);
}
