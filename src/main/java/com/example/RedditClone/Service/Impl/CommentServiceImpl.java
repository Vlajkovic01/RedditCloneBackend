package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Model.Enum.ReactionType;
import com.example.RedditClone.Repository.CommentRepository;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.CommentService;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentServiceImpl implements CommentService {

    private final ReactionRepository reactionRepository;

    private final PostService postService;

    private final UserService userService;

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, PostService postService, ReactionRepository reactionRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Comment findCommentById(Integer id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public Comment createComment(CommentCreateRequestDTO commentCreateRequestDTO, Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return null;
        }

        Comment newComment = new Comment();
        newComment.setText(commentCreateRequestDTO.getText());
        newComment.setTimestamp(LocalDate.now());
        newComment.setIsDeleted(false);
        newComment.setUser(currentLoggedUser);

        if (commentCreateRequestDTO.getPostId() != 0) {
            newComment.setPost(postService.findPostById(commentCreateRequestDTO.getPostId()));
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
}
