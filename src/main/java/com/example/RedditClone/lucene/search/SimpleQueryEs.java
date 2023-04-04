package com.example.RedditClone.lucene.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleQueryEs {
    private String field;
    private String value;
}
