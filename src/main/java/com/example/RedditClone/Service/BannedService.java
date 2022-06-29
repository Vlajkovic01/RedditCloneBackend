package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Banned.Request.BannedCreateDTO;
import com.example.RedditClone.Model.Entity.Banned;
import org.springframework.security.core.Authentication;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface BannedService {
    Banned createBan(BannedCreateDTO bannedCreateDTO, Authentication authentication);
    List<Banned> findAllByCommunityId(Integer communityId);
    Banned  findBannedByCommunityIdAndUserUsername(Integer communityId, String username);

    boolean delete(Integer communityId, String username);
}
