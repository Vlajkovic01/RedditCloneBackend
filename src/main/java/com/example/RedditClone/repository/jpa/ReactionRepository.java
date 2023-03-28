package com.example.RedditClone.repository.jpa;

import com.example.RedditClone.model.entity.Comment;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.entity.Reaction;
import com.example.RedditClone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Transactional
    void deleteAllByPost(Post post);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reactions where reaction_id = ?")
    void deleteReaction(Integer reactionId);

    List<Reaction> findAllByPostId(Integer id);

    Reaction findReactionByPostAndUser(Post post, User user);
    Reaction findReactionByCommentAndUser(Comment comment, User user);
    Reaction findReactionById(Integer reactionId);
}
