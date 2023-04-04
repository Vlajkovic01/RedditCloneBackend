package com.example.RedditClone.lucene.search;

import org.elasticsearch.index.query.QueryBuilder;

public class SearchQueryGenerator {

    public static QueryBuilder createMatchQueryBuilder(SimpleQueryEs simpleQueryEs) {
        if(simpleQueryEs.getValue().startsWith("\"") && simpleQueryEs.getValue().endsWith("\"")) {
            return QueryBuilderCustom.buildQuery(SearchType.PHRASE, simpleQueryEs.getField(), simpleQueryEs.getValue());
        } else {
            return QueryBuilderCustom.buildQuery(SearchType.MATCH, simpleQueryEs.getField(), simpleQueryEs.getValue());
        }
    }

    public static QueryBuilder createRangeQueryBuilder(SimpleQueryEs simpleQueryEs) {
        return QueryBuilderCustom.buildQuery(SearchType.RANGE, simpleQueryEs.getField(), simpleQueryEs.getValue());
    }
}
