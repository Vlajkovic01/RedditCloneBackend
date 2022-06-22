package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Report;
import com.example.RedditClone.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findReportByPostAndByUser(Post post, User user);
    Report findReportByCommentAndByUser(Comment comment, User user);
    List<Report> findAllByCommunityIdAndAcceptedIsFalse(Integer communityId);
}
