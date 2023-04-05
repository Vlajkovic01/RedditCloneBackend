package com.example.RedditClone.model.mapper;

import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Rule;
import com.example.RedditClone.model.indexed.IndexedCommunity;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class IndexedCommunityMapper {

    public static IndexedCommunity mapIndexedCommunity(Community community){
        return IndexedCommunity.builder()
                .mySqlId(community.getId())
                .name(community.getName())
                .description(community.getDescription())
                .rules(community.getRules().stream().map(Rule::getDescription).toList())
                .numOfPosts(community.getPosts().size())
                .build();
    }

    public static List<IndexedCommunity> mapHits(SearchHits<IndexedCommunity> searchHits) {
        return searchHits
                .map(SearchHit::getContent)
                .toList();
    }
}
