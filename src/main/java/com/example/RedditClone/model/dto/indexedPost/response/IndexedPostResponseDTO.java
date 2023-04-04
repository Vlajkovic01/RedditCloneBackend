package com.example.RedditClone.model.dto.indexedPost.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndexedPostResponseDTO {

    private String title;
    private String text;
    private String highlighter;
}
