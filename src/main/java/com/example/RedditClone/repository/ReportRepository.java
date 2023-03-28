package com.example.RedditClone.repository;

import com.example.RedditClone.model.entity.Comment;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.entity.Report;
import com.example.RedditClone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findReportByPostAndByUser(Post post, User user);
    Report findReportByCommentAndByUser(Comment comment, User user);
    Report findReportById(Integer reportId);
    @Query(nativeQuery = true, value = "select * from reports where accepted = false and community_id = ?")
    List<Report> findAllByCommunityIdAndAcceptedIsFalseMyQuery(Integer communityId);
    List<Report> findAllByPostIdAndAcceptedFalse(Integer postId);
    List<Report> findAllByCommentIdAndAcceptedFalse(Integer commentId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reports where post_id = ? and accepted = false")
    void deleteByPostId(Integer postId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reports where comment_id = ? and accepted = false")
    void deleteByCommentId(Integer commentId);
    @Query(nativeQuery = true, value = "select * " +
            "from reports r left join comments c on r.comment_id = c.comment_id " +
            "where c.post_id = ? and r.accepted = false")
    List<Report> findAllByComment_PostIdAndAcceptedIsFalseMyQuery(Integer postId);
}
