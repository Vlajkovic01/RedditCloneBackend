package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Post;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication);
}
