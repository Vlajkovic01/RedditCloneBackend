package com.example.RedditClone.repository.elasticsearch;

import com.example.RedditClone.model.entity.Community;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityElasticsearchRepository extends ElasticsearchRepository<Community, Integer> {
    List<Community> findAllByName(String name);
}
