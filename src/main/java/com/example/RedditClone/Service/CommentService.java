package com.example.RedditClone.Service;

import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Post;

public interface CommentService {

    Comment findCommentById(Integer id);
}
