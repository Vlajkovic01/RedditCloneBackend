package com.example.RedditClone.model.dto.indexedPost.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndexedPostSearchDTO {
    private String title;

    private String text;

    private String textPdf;

    private String commentText;

    private Integer karmaFrom;

    private Integer karmaTo;

    private String flair;

    private Integer numOfCommentsFrom;

    private Integer numOfCommentsTo;
}
