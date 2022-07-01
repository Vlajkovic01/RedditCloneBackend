package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Report.Request.ReportCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.Report;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.ReportRepository;
import com.example.RedditClone.Service.*;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService {

    private final CommunityService communityService;
    private final CommentService commentService;
    private final PostService postService;
    private final ReportRepository reportRepository;
    private final UserService userService;
    private final LogService logService;

    public ReportServiceImpl(LogService logService, UserService userService, ReportRepository reportRepository, PostService postService, CommentService commentService, CommunityService communityService) {
        this.logService = logService;
        this.userService = userService;
        this.reportRepository = reportRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.communityService = communityService;
    }

    @Override
    public Report createReport(ReportCreateRequestDTO reportCreateRequestDTO, Authentication authentication) {
        logService.message("Report service, createReport() method called.", MessageType.INFO);

        if (authentication == null) {
            logService.message("Report service, createReport() method, authentication is null.", MessageType.WARN);
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Report service, createReport() method, current logged user is null.", MessageType.WARN);
            return null;
        }

        Report newReport = new Report();
        newReport.setReason(reportCreateRequestDTO.getReason());
        newReport.setTimestamp(LocalDate.now());
        newReport.setAccepted(false);
        newReport.setByUser(currentLoggedUser);

        if (reportCreateRequestDTO.getCommunityId() != 0) {
            if (communityService.findCommunityById(reportCreateRequestDTO.getCommunityId()) == null) {
                return null;
            }
            newReport.setCommunity(communityService.findCommunityById(reportCreateRequestDTO.getCommunityId()));
        }

        if (reportCreateRequestDTO.getPostId() != 0) {
            Report reportPost = reportRepository.findReportByPostAndByUser(
                    postService.findPostById(reportCreateRequestDTO.getPostId()),
                    currentLoggedUser);
            if (reportPost != null) {
                return null;
            }
            newReport.setPost(postService.findPostById(reportCreateRequestDTO.getPostId()));
        }

        if (reportCreateRequestDTO.getCommentId() != 0) {
            Report reportComment = reportRepository.findReportByCommentAndByUser(
                    commentService.findCommentById(reportCreateRequestDTO.getCommentId()),
                    currentLoggedUser);
            if (reportComment != null) {
                return null;
            }
            newReport.setComment(commentService.findCommentById(reportCreateRequestDTO.getCommentId()));
        }

        reportRepository.save(newReport);

        return newReport;
    }

    @Override
    public Report acceptReport(Integer id) {
        logService.message("Report service, acceptReport() method called.", MessageType.INFO);

        Report report = reportRepository.findReportById(id);
        if (report == null) {
            logService.message("Report service, acceptReport() method, unable to find report.", MessageType.INFO);
            return null;
        }

        report.setAccepted(true);
        if (report.getComment() != null) {
            Comment comment = commentService.findCommentById(report.getComment().getId());
            comment.setIsDeleted(true);
        }
        reportRepository.save(report);

        if (report.getPost() != null) {
            reportRepository.deleteByPostId(report.getPost().getId());
        }

        return report;
    }

    @Override
    public List<Report> findAllByCommunityId(Integer id) {
        logService.message("Report service, findAllByCommunityId() method called.", MessageType.INFO);
        return reportRepository.findAllByCommunityIdAndAcceptedIsFalse(id);
    }
}
