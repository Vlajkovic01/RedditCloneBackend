package com.example.RedditClone.lucene.search;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

@Getter
@Setter
public class QueryBuilderCustom {
    private static int fuzziness = 2;

    public static QueryBuilder buildQuery(List<SearchQuery> params) throws IllegalArgumentException {
        for (SearchQuery q : params){
            validateQueryFields(q.getField(), q.getValue());
        }
        if (params.size() > 1 && params.size() < 3){
            if (params.get(0).getOperator().equals(BoolOperator.AND)){
                return QueryBuilders.boolQuery().must(constructQuery(params.get(0))).must(constructQuery(params.get(1)));
            }else{
                return QueryBuilders.boolQuery().should(constructQuery(params.get(0))).should(constructQuery(params.get(1)));
            }
        }else if(params.size() > 2){
            throw new IllegalArgumentException("Can only search by two parameters!");
        }else{
            return constructQuery(params.get(0));
        }
    }


    private static QueryBuilder constructQuery(SearchQuery query){
        if(query.getSearchType().equals(SearchType.TERM)){
            return QueryBuilders.termQuery(query.getField(),query.getValue());
        } else if(query.getSearchType().equals(SearchType.MATCH)){
            return QueryBuilders.matchQuery(query.getField(),query.getValue());
        } else if(query.getSearchType().equals(SearchType.PHRASE)){
            return QueryBuilders.matchPhraseQuery(query.getField(),query.getValue());
        } else if(query.getSearchType().equals(SearchType.FUZZY)){
            return QueryBuilders.fuzzyQuery(query.getField(),query.getValue()).fuzziness(Fuzziness.fromEdits(fuzziness));
        } else if(query.getSearchType().equals(SearchType.PREFIX)){
            return QueryBuilders.prefixQuery(query.getField(),query.getValue());
        } else if(query.getSearchType().equals(SearchType.RANGE)){
            String[] values = query.getValue().split(":");
            return QueryBuilders.rangeQuery(query.getField()).from(values[0]).to(values[1]);
        } else{
            return QueryBuilders.matchPhraseQuery(query.getField(),query.getValue());
        }
    }

    private static void validateQueryFields(String field, String value) {
        String errorMessage = "";
        if(field == null || field.equals("")){
            errorMessage += "Field not specified";
        }
        if(value == null){
            if(!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value not specified";
        }
        if(!errorMessage.equals("")){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static SearchType generateType(String value,boolean fuzzy){
        if (value.startsWith("*") && value.endsWith("*")){
            return SearchType.PHRASE;
        }
        if (fuzzy){
            return SearchType.FUZZY;
        } else{
            return SearchType.MATCH;
        }
    }

    public static BoolOperator genereateBoolOperator(String value){
        if(value == null){
            return BoolOperator.AND;
        }else{
            return  BoolOperator.valueOf(value);
        }
    }
}
