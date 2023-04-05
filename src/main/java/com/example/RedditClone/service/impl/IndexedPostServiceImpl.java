package com.example.RedditClone.service.impl;

import com.example.RedditClone.lucene.search.SearchQueryGenerator;
import com.example.RedditClone.lucene.search.SimpleQueryEs;
import com.example.RedditClone.model.dto.indexedPost.request.IndexedPostSearchDTO;
import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.indexed.IndexedPost;
import com.example.RedditClone.model.mapper.IndexedPostMapper;
import com.example.RedditClone.repository.elasticsearch.IndexedPostRepository;
import com.example.RedditClone.service.IndexedPostService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexedPostServiceImpl implements IndexedPostService {
    private final IndexedPostRepository indexedPostRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public IndexedPostServiceImpl(IndexedPostRepository indexedPostRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.indexedPostRepository = indexedPostRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }


    @Override
    public void indexPost(Post post) {
        indexedPostRepository.save(IndexedPostMapper.mapIndexedPost(post));
    }

    @Override
    public List<IndexedPost> findAllByTitleText(IndexedPostSearchDTO searchDTO) {
        QueryBuilder titleQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("title", searchDTO.getTitle()));
        QueryBuilder textQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("text", searchDTO.getText()));

        BoolQueryBuilder boolQueryTitleText = QueryBuilders
                .boolQuery()
                .should(titleQuery)
                .should(textQuery);

        return IndexedPostMapper.mapHits(searchByBoolQuery(boolQueryTitleText));
    }

    @Override
    public List<IndexedPost> findAllByTitle(String title) {
        return indexedPostRepository.findAllByTitle(title);
    }

    @Override
    public List<IndexedPost> findAllByText(String text) {
        return indexedPostRepository.findAllByText(text);
    }

    private SearchHits<IndexedPost> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, IndexedPost.class,  IndexCoordinates.of("posts"));
    }
}
