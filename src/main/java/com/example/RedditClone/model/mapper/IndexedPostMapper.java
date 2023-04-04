package com.example.RedditClone.model.mapper;

import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.indexed.IndexedPost;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class IndexedPostMapper {

    public static IndexedPost mapIndexedPost(PostCreateRequestDTO postCreateRequestDTO){
        return IndexedPost.builder()
                .title(postCreateRequestDTO.getTitle())
                .text(postCreateRequestDTO.getText())
                .flair(postCreateRequestDTO.getFlair().getName())
                .karma(1)
                .build();
    }

    public static List<IndexedPost> mapHits(SearchHits<IndexedPost> searchHits) {
        return searchHits
                .map(SearchHit::getContent)
                .toList();
    }
}
