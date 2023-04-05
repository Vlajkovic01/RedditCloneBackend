package com.example.RedditClone.repository.elasticsearch;

import com.example.RedditClone.model.indexed.IndexedCommunity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexedCommunityRepository extends ElasticsearchRepository<IndexedCommunity, Integer> {

    List<IndexedCommunity> findAllByName(String name);
    List<IndexedCommunity> findAllByDescription(String description);
}
