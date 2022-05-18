package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
    List<Rule> findAllByCommunity(Community community);
    @Transactional
    void deleteAllByCommunityId(Integer id);
}
