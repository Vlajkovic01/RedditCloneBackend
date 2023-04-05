package com.example.RedditClone.model.dto.indexedCommunity.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndexedCommunityResponseDTO {

    private String name;

    private Integer numOfPosts;

    private Double averageKarma;

    private String highlighter;
}
