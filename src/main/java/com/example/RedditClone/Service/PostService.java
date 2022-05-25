package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post findPostById(Integer id);
    Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication, Community community);
    Post editPost(PostEditRequestDTO postEditRequestDTO, Post postForEdit);

    void deletePost(Integer idPost);
}
