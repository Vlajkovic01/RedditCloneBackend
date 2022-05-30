package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Transactional
    void deleteAllByPost(Post post);

    List<Reaction> findAllByPostId(Integer id);

    Reaction findReactionByPostAndUser(Post post, User user);
}
