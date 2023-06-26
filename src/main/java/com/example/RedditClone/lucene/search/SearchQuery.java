package com.example.RedditClone.lucene.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchQuery {
    private String field;
    private String value;
    private SearchType searchType;
    private BoolOperator operator;
}
