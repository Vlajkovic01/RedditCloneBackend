package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Transactional
    void deleteAllByPost(Post post);
}
