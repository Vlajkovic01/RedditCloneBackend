package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
    List<Community> findAll();
    Community findCommunityByName(String name);
}
