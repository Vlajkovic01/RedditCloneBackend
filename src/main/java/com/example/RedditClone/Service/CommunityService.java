package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface CommunityService {
    List<Community> findAll();

    Community createCommunity(CommunityCreateRequestDTO communityCreateRequestDTO, Authentication authentication);
}
