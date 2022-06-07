package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Repository.CommentRepository;
import com.example.RedditClone.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findCommentById(Integer id) {
        return commentRepository.findCommentById(id);
    }
}
