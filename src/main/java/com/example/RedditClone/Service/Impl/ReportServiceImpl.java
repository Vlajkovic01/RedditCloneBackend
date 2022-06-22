package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Report.Request.ReportCreateRequestDTO;
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

@Service
public class ReportServiceImpl implements ReportService {
    private final CommentService commentService;
    private final PostService postService;
    private final ReportRepository reportRepository;
    private final UserService userService;
    private final LogService logService;

    public ReportServiceImpl(LogService logService, UserService userService, ReportRepository reportRepository, PostService postService, CommentService commentService) {
        this.logService = logService;
        this.userService = userService;
        this.reportRepository = reportRepository;
        this.postService = postService;
        this.commentService = commentService;
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
}
