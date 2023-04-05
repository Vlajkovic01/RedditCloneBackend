package com.example.RedditClone.model.mapper;

import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.entity.Comment;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.indexed.IndexedPost;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class IndexedPostMapper {

    public static IndexedPost mapIndexedPost(Post post){
        return IndexedPost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .flair(post.getFlair().getName())
                .comments(post.getComments().stream().map(Comment::getText).toList())
                .karma(1)
                .build();
    }

    public static List<IndexedPost> mapHits(SearchHits<IndexedPost> searchHits) {
        return searchHits
                .map(SearchHit::getContent)
                .toList();
    }
}
