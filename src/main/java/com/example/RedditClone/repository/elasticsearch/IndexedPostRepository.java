package com.example.RedditClone.repository.elasticsearch;

import com.example.RedditClone.model.indexed.IndexedPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexedPostRepository extends ElasticsearchRepository<IndexedPost, Integer> {
    void deleteById(Integer id);
    void deleteIndexedPostsByCommunityId(Integer communityId);
}
