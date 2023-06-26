package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.indexedPost.request.IndexedPostSearchDTO;
import com.example.RedditClone.model.dto.indexedPost.response.IndexedPostResponseDTO;
import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.indexed.IndexedPost;

import java.util.List;
import java.util.Map;

public interface IndexedPostService {
    void indexPost(Post post);
    List<IndexedPostResponseDTO> search(Map<String, String> params, Integer communityId) throws IllegalArgumentException;

}
