package com.example.RedditClone.repository.jpa;

import com.example.RedditClone.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
    List<Community> findAll();
    @Query(nativeQuery = true, value = "select * from communities where is_suspended = false " +
            "order by  rand() limit 12")
    List<Community> find12RandomCommunities();
    Community findCommunityById(Integer id);
    Community findCommunityByName(String name);
}
