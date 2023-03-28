package com.example.RedditClone.repository.jpa;

import com.example.RedditClone.model.entity.Comment;
import com.example.RedditClone.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findCommentById(Integer id);
    Set<Comment> findCommentsByPostOrderByTimestampDesc(Post post);
    Set<Comment> findCommentsByPostOrderByTimestampAsc(Post post);
    @Query(nativeQuery = true, value = "select *, (select count(*) " +
            "            from reactions r left join comments c on r.comment_id = c.comment_id " +
            "            where r.type = 'UPVOTE' and c.comment_id = comments.comment_id) - (select count(*) " +
            "            from reactions r left join comments c on r.comment_id = c.comment_id " +
            "            where r.type = 'DOWNVOTE' and c.comment_id = comments.comment_id) as karma " +
            "from comments comments where comments.post_id = ? " +
            "order by karma desc")
    Set<Comment> findAllByPostOrderByKarma(Integer id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update comments set is_deleted = true where comment_id = ?")
    void setDeletedById(Integer commentId);
}
