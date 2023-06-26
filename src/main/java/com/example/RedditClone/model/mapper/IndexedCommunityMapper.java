package com.example.RedditClone.model.mapper;

import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Rule;
import com.example.RedditClone.model.indexed.IndexedCommunity;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class IndexedCommunityMapper {

    public static IndexedCommunity mapIndexedCommunity(Community community, String pdfText){
        return IndexedCommunity.builder()
                .id(community.getId())
                .name(community.getName())
                .description(community.getDescription())
                .rules(community.getRules().stream().map(Rule::getDescription).toList())
                .numOfPosts(community.getPosts().size())
                .avgKarma(0)
                .pdfText(pdfText)
                .build();
    }

    public static List<IndexedCommunity> mapHits(SearchHits<IndexedCommunity> searchHits) {
        return searchHits
                .map(SearchHit::getContent)
                .toList();
    }
}
