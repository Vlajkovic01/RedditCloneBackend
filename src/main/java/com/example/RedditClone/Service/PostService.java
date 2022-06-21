package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
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
