package com.example.RedditClone.model.dto.indexedCommunity.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndexedCommunitySearchDTO {
    private String name;

    private String description;

    private String descriptionPdf;

    private String rules;

    private Integer averageKarmaFrom;

    private Integer averageKarmaTo;

    private Integer numOfPostsFrom;

    private Integer numOfPostsTo;
}
