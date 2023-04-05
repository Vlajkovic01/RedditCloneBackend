package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.indexedPost.request.IndexedPostSearchDTO;
import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.indexed.IndexedPost;

import java.util.List;

public interface IndexedPostService {
    void indexPost(Post post);
    List<IndexedPost> findAllByTitleText(IndexedPostSearchDTO searchDTO);
    List<IndexedPost> findAllByTitle(String title);
    List<IndexedPost> findAllByText(String text);
}
