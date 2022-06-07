package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;

public interface CommentService {

    Comment findCommentById(Integer id);
    Comment createComment(CommentCreateRequestDTO commentCreateRequestDTO, Authentication authentication);
}
