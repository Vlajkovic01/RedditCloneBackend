package com.example.RedditClone.model.dto.indexedPost.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexedPostResponseDTO {

    private Integer id;
    private String title;
    private String text;
    private String flair;
    private String highlighter;
}
