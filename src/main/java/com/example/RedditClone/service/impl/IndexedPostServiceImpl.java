package com.example.RedditClone.service.impl;

import com.example.RedditClone.lucene.search.BoolOperator;
import com.example.RedditClone.lucene.search.QueryBuilderCustom;
import com.example.RedditClone.lucene.search.SearchQuery;
import com.example.RedditClone.lucene.search.SearchType;
import com.example.RedditClone.model.dto.indexedPost.request.IndexedPostSearchDTO;
import com.example.RedditClone.model.dto.indexedPost.response.IndexedPostResponseDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.indexed.IndexedPost;
import com.example.RedditClone.model.mapper.IndexedPostMapper;
import com.example.RedditClone.repository.elasticsearch.IndexedPostRepository;
import com.example.RedditClone.service.IndexedPostService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
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
public class IndexedPostServiceImpl implements IndexedPostService {
    public static final int RANGE_TO_MAX = 100000000;
    private final IndexedPostRepository indexedPostRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public IndexedPostServiceImpl(IndexedPostRepository indexedPostRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.indexedPostRepository = indexedPostRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }


    @Override
    public void indexPost(Post post, String pdfText) {
        indexedPostRepository.save(IndexedPostMapper.mapIndexedPost(post, pdfText));
    }

    @Override
    public List<IndexedPostResponseDTO> search(Map<String, String> params, Integer communityId) throws IllegalArgumentException{
        List< SearchQuery> queries = new ArrayList<>();
        if (!params.containsKey("logic") && params.size() > 2){
            throw  new IllegalArgumentException("You must provide logic for multiple search fields");
        }
        validateAndAddRangeFields(params, queries);

        for (Map.Entry<String, String> entry : params.entrySet()){
            if (!entry.getKey().contains("karma") && !entry.getKey().contains("fuzzy") && !entry.getKey().contains("logic") ){
                queries.add(new SearchQuery(entry.getKey(),entry.getValue(), QueryBuilderCustom.generateType(entry.getValue(),Boolean.parseBoolean(params.get("fuzzy"))),QueryBuilderCustom.genereateBoolOperator(params.get("logic"))));
            }
        }
        SearchHits<IndexedPost> posts = searchQuery(QueryBuilderCustom.buildQuery(queries),communityId);
        List<IndexedPostResponseDTO> returnList = new ArrayList<>();

        for (SearchHit<IndexedPost> hit : posts.getSearchHits()){
            Map<String,List<String> > highlightFields = hit.getHighlightFields();
            List<String> highlightList = highlightFields.get("text") == null ?  highlightFields.get("pdfText") : highlightFields.get("text");
            StringBuffer highlightText  = new StringBuffer();
            if (highlightList != null){
                for (String s : highlightList){
                    highlightText.append(s);
                }
            }
            returnList.add(new IndexedPostResponseDTO(hit.getContent().getId(),hit.getContent().getTitle(),hit.getContent().getText(),hit.getContent().getFlair(),highlightText.toString()));
        }
        return returnList;
    }

    @Override
    public void deleteIndexedPostsByCommunityId(Integer communityId) {
        indexedPostRepository.deleteIndexedPostsByCommunityId(communityId);
    }

    @Override
    public void deleteById(Integer id) {
        indexedPostRepository.deleteById(id);
    }

    @Override
    public IndexedPost findById(Integer id) {
        return indexedPostRepository.findById(id).orElse(null);
    }

    private  void validateAndAddRangeFields(Map<String, String> params, List<SearchQuery> queries) {
        if (params.containsKey("karmaFrom") || params.containsKey("karmaTo")){
            Integer karmaFrom = 0;
            Integer karmaTo = RANGE_TO_MAX;
            if(params.containsKey("karmaFrom")){
                karmaFrom = Integer.parseInt(params.get("karmaFrom"));
            }
            if(params.containsKey("karmaTo")){
                karmaTo = Integer.parseInt(params.get("karmaTo"));
            }
            if (karmaFrom > karmaTo){
                throw new IllegalArgumentException("Post karma from must be smaller then post karma to");
            }else{
                queries.add(new SearchQuery("karma",String.format("%s:%s",karmaFrom,karmaTo), SearchType.RANGE, BoolOperator.valueOf(params.get("logic"))));
            }
        }
    }


    private SearchHits<IndexedPost> searchQuery(QueryBuilder queryBuilder,Integer communityId){
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("text")
                .field("pdfText")
                .preTags("<strong>")
                .postTags("</strong>");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().must(queryBuilder).must((QueryBuilders.matchQuery("communityId",communityId))))
                .withHighlightBuilder(highlightBuilder)
                .build();
        return elasticsearchRestTemplate.search(searchQuery, IndexedPost.class,  IndexCoordinates.of("posts"));
    }
}
