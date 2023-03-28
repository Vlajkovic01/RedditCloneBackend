package com.example.RedditClone.repository;

import com.example.RedditClone.model.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {
    Banned findBannedByCommunityIdAndUserId(Integer communityId, Integer userId);
    List<Banned> findAllByCommunityId(Integer communityId);
    Banned findBannedByCommunityIdAndUserUsername(Integer communityId, String username);
}
