package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
    List<Community> findAll();
}
