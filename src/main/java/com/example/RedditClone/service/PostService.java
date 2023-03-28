package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.dto.post.request.PostEditRequestDTO;
import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Post;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface PostService {
    List<Post> findAll();
    List<Post> find12RandomPosts();
    List<Post> newSort();
    List<Post> newSortInCommunity(Community community);
    Set<Post> topSort();
    Set<Post> topSortInCommunity(Community community);
    Set<Post> hotSort();
    Set<Post> hotSortInCommunity(Community community);
    Post findPostById(Integer id);
    Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication, Community community);
    Post editPost(PostEditRequestDTO postEditRequestDTO, Post postForEdit);

    void deletePost(Integer idPost);
}
