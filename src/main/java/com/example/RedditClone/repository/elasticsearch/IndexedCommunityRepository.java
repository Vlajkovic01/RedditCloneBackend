package com.example.RedditClone.repository.elasticsearch;

import com.example.RedditClone.model.indexed.IndexedCommunity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexedCommunityRepository extends ElasticsearchRepository<IndexedCommunity, Integer> {
    Optional<IndexedCommunity> findById(Integer id);
    void deleteById(Integer id);
}
