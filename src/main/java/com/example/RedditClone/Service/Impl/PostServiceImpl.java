package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Repository.PostRepository;
import com.example.RedditClone.Service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
