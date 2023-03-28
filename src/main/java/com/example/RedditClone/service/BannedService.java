package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.banned.request.BannedCreateDTO;
import com.example.RedditClone.model.entity.Banned;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BannedService {
    Banned createBan(BannedCreateDTO bannedCreateDTO, Authentication authentication);
    List<Banned> findAllByCommunityId(Integer communityId);
    Banned  findBannedByCommunityIdAndUserUsername(Integer communityId, String username);

    boolean delete(Integer communityId, String username);
}
