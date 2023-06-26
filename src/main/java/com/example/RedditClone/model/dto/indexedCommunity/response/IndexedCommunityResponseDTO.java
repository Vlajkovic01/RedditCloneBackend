package com.example.RedditClone.model.dto.indexedCommunity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexedCommunityResponseDTO {

    private Integer id;

    private String name;

    private Integer numOfPosts;

    private Integer averageKarma;

    private String highlighter;
}
