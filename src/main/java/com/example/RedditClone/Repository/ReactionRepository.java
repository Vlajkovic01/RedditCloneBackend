package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

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
