package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface CommentService {

    Comment findCommentById(Integer id);
    Comment createComment(CommentCreateRequestDTO commentCreateRequestDTO, Authentication authentication);
    Set<Comment> newSort(Post post);
    Set<Comment> oldSort(Post post);
    Set<Comment> topSort(Post post);
//    Void removeChildrens(Set<Comment> comments);
    void save(Comment comment);
    void delete(Comment comment);
}
