package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Model.Enum.ReactionType;
import com.example.RedditClone.Repository.CommentRepository;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Repository.ReportRepository;
import com.example.RedditClone.Service.*;
import com.example.RedditClone.Util.MessageType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    private final ReportRepository reportRepository;
    private final BannedService bannedService;
    private final LogService logService;
    private final ReactionRepository reactionRepository;

    private final PostService postService;

    private final UserService userService;

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, PostService postService, ReactionRepository reactionRepository, LogService logService, BannedService bannedService, ReportRepository reportRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.reactionRepository = reactionRepository;
        this.logService = logService;
        this.bannedService = bannedService;
        this.reportRepository = reportRepository;
    }

    @Override
    public Comment findCommentById(Integer id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public Comment createComment(CommentCreateRequestDTO commentCreateRequestDTO, Authentication authentication) {

        logService.message("Comment service, createComment() method called.", MessageType.INFO);

        if (authentication == null) {
            logService.message("Comment service, createComment() method, authentication is null.", MessageType.WARN);
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Comment service, createComment() method, current logged user is null.", MessageType.WARN);
            return null;
        }

        Comment newComment = new Comment();
        newComment.setText(commentCreateRequestDTO.getText());
        newComment.setTimestamp(LocalDate.now());
        newComment.setIsDeleted(false);
        newComment.setUser(currentLoggedUser);

        if (commentCreateRequestDTO.getPostId() != 0) {
            Post post = postService.findPostById(commentCreateRequestDTO.getPostId());
            if (bannedService.findBannedByCommunityIdAndUserUsername(post.getCommunity().getId(), currentLoggedUser.getUsername()) != null) {
                logService.message("Comment service, createComment() method, current logged user is banned from this community.", MessageType.WARN);
                return null;
            }
            newComment.setPost(post);
        }

        if (commentCreateRequestDTO.getParentCommentId() != 0) {
            newComment.setParent(findCommentById(commentCreateRequestDTO.getParentCommentId()));
        }

        Reaction newReaction = new Reaction();
        newReaction.setType(ReactionType.UPVOTE);
        newReaction.setTimestamp(LocalDate.now());
        newReaction.setUser(currentLoggedUser);
        newReaction.setComment(newComment);


        newComment = commentRepository.save(newComment);
        newReaction = reactionRepository.save(newReaction);
        return newComment;
    }

    @Override
    public Set<Comment> newSort(Post post) {
        return commentRepository.findCommentsByPostOrderByTimestampDesc(post);
    }

    @Override
    public Set<Comment> oldSort(Post post) {
        return commentRepository.findCommentsByPostOrderByTimestampAsc(post);
    }

    @Override
    public Set<Comment> topSort(Post post) {
        return commentRepository.findAllByPostOrderByKarma(post.getId());
    }

    @Override
    public Void removeChildren(Set<Comment> comments) {
        Set<Comment> nextSet = new HashSet<>();
        for (Comment c : comments) {

            if (!reportRepository.findAllByCommentIdAndAcceptedFalse(c.getId()).isEmpty()) {
                reportRepository.deleteByCommentId(c.getId());
            }

            c.setIsDeleted(true);
            commentRepository.save(c);

            if (!c.getChildren().isEmpty()) {
                c.getChildren().forEach(comment -> nextSet.add(comment));
            }
        }

        if (nextSet.isEmpty()) {
            return null;
        }

        return removeChildren(nextSet);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void setDeletedById(Integer commentId) {
        commentRepository.setDeletedById(commentId);
    }
}
