package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
    List<Community> findAll();
    @Query(nativeQuery = true, value = "select * from communities where is_suspended = false " +
            "order by  rand() limit 12")
    List<Community> find12RandomCommunities();
    Community findCommunityById(Integer id);
    Community findCommunityByName(String name);
}
