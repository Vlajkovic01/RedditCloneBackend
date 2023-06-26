package com.example.RedditClone.service.impl;

import com.example.RedditClone.lucene.search.BoolOperator;
import com.example.RedditClone.lucene.search.QueryBuilderCustom;
import com.example.RedditClone.lucene.search.SearchQuery;
import com.example.RedditClone.lucene.search.SearchType;
import com.example.RedditClone.model.dto.community.request.CommunityCreateRequestDTO;
import com.example.RedditClone.model.dto.indexedCommunity.response.IndexedCommunityResponseDTO;
import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.indexed.IndexedCommunity;
import com.example.RedditClone.model.mapper.IndexedCommunityMapper;
import com.example.RedditClone.repository.elasticsearch.IndexedCommunityRepository;
import com.example.RedditClone.service.IndexedCommunityService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IndexedCommunityServiceImpl implements IndexedCommunityService {
    public static final int RANGE_TO_MAX = 100000000;
    private final IndexedCommunityRepository indexedCommunityRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public IndexedCommunityServiceImpl(IndexedCommunityRepository indexedCommunityRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.indexedCommunityRepository = indexedCommunityRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public void indexCommunity(Community community) {
        indexedCommunityRepository.save(IndexedCommunityMapper.mapIndexedCommunity(community));
    }

    @Override
    public List<IndexedCommunityResponseDTO> search(Map<String, String> params) throws IllegalArgumentException {
        List<SearchQuery> queries = new ArrayList<>();
        if (!params.containsKey("logic") && params.size() > 2){
            throw new IllegalArgumentException("You must provide logic for multiple search fields");
        }
        validateAndAddRangeFields(params, queries);

        for (Map.Entry<String, String> entry : params.entrySet()){
            if (!entry.getKey().contains("avgKarma") && !entry.getKey().contains("numOfPosts") && !entry.getKey().contains("fuzzy") && !entry.getKey().contains("logic") ){
                queries.add(new SearchQuery(entry.getKey(),entry.getValue(), QueryBuilderCustom.generateType(entry.getValue(),Boolean.parseBoolean(params.get("fuzzy"))),QueryBuilderCustom.genereateBoolOperator(params.get("logic"))));
            }
        }
        SearchHits<IndexedCommunity> communities = searchQuery(QueryBuilderCustom.buildQuery(queries));
        List<IndexedCommunityResponseDTO> returnList = new ArrayList<>();
        for (SearchHit<IndexedCommunity> hit : communities.getSearchHits()){
            Map<String,List<String> > highlightFields = hit.getHighlightFields();
            List<String> highlightList = highlightFields.get("description") == null ?  highlightFields.get("pdfText") : highlightFields.get("description");
            if (highlightList == null){
                highlightList = highlightFields.get("rules");
            }
            StringBuffer highlightText  = new StringBuffer();
            if (highlightList != null){
                for (String s : highlightList){
                    highlightText.append(s);
                }
            }
            returnList.add(new IndexedCommunityResponseDTO(hit.getContent().getId(),hit.getContent().getName(),hit.getContent().getNumOfPosts(),hit.getContent().getAvgKarma(),highlightText.toString()));
        }
        return returnList;
    }

    private void validateAndAddRangeFields(Map<String, String> params, List<SearchQuery> queries) {
        if (params.containsKey("avgKarmaFrom") || params.containsKey("avgKarmaTo")){
            Integer karmaFrom = 0;
            Integer karmaTo = RANGE_TO_MAX;
            if(params.containsKey("avgKarmaFrom")){
                karmaTo = Integer.parseInt(params.get("avgKarmaFrom"));
            }
            if(params.containsKey("avgKarmaTo")){
                karmaTo = Integer.parseInt(params.get("avgKarmaTo"));
            }
            if (karmaFrom > karmaTo){
                throw new IllegalArgumentException("Avg karma from must be smaller then avg karma to");
            }else{
                queries.add(new SearchQuery("avgKarma",String.format("%s:%s",karmaFrom,karmaTo), SearchType.RANGE, BoolOperator.valueOf(params.get("logic"))));
            }
        }
        if (params.containsKey("numOfPostsFrom") || params.containsKey("numOfPostsTo")){
            Integer postsFrom = 0;
            Integer postsTo = RANGE_TO_MAX;
            if(params.containsKey("numOfPostsFrom")){
                postsFrom = Integer.parseInt(params.get("numOfPostsFrom"));
            }
            if(params.containsKey("numOfPostsTo")){
                postsTo = Integer.parseInt(params.get("numOfPostsTo"));
            }
            if (postsFrom > postsTo){
                throw new IllegalArgumentException("Post count from must be smaller then post count to");
            }else{
                queries.add(new SearchQuery("numOfPosts",String.format("%s:%s",postsFrom,postsTo), SearchType.RANGE, BoolOperator.valueOf(params.get("logic"))));
            }
        }
    }

    private SearchHits<IndexedCommunity> searchQuery(QueryBuilder queryBuilder){

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("description")
                .field("pdfText")
                .field("rules")
                .preTags("<strong>")
                .postTags("</strong>");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .build();
        return elasticsearchRestTemplate.search(searchQuery, IndexedCommunity.class,  IndexCoordinates.of("communities"));
    }
}
