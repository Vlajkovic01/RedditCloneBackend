package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunityEditRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface CommunityService {
    List<Community> findAll();
    List<Community> find12RandomCommunities();
    Community findCommunityById(Integer id);
    Community createCommunity(CommunityCreateRequestDTO communityCreateRequestDTO, Authentication authentication);
    Community editCommunity(CommunityEditRequestDTO communityEditRequestDTO, Community community);

    Community save(Community community);

    void deleteCommunity(Community community);
}
