package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.comment.request.CommentCreateRequestDTO;
import com.example.RedditClone.model.entity.Comment;
import com.example.RedditClone.model.entity.Post;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface CommentService {

    Comment findCommentById(Integer id);
    Comment createComment(CommentCreateRequestDTO commentCreateRequestDTO, Authentication authentication);
    Set<Comment> newSort(Post post);
    Set<Comment> oldSort(Post post);
    Set<Comment> topSort(Post post);
    Void removeChildren(Set<Comment> comments);
    void save(Comment comment);
    void delete(Comment comment);
    void setDeletedById(Integer commentId);
}
