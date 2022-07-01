package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Report;
import com.example.RedditClone.Model.Entity.User;
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
    List<Report> findAllByCommunityIdAndAcceptedIsFalse(Integer communityId);
    List<Report> findAllByPostIdAndAcceptedFalse(Integer postId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reports where post_id = ? and accepted = false")
    void deleteByPostId(Integer postId);
}
