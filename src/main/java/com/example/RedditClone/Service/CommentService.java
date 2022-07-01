package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
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
