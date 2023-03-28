package com.example.RedditClone.repository;

import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
    List<Rule> findAllByCommunity(Community community);
    @Transactional
    void deleteAllByCommunityId(Integer id);
}
