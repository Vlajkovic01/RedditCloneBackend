package com.example.RedditClone.repository.elasticsearch;

import com.example.RedditClone.model.indexed.IndexedPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexedPostRepository extends ElasticsearchRepository<IndexedPost, Integer> {
}
