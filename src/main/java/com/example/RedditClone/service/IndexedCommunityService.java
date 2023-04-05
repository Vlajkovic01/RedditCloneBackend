package com.example.RedditClone.service;

import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.indexed.IndexedCommunity;

import java.util.List;

public interface IndexedCommunityService {

    void indexCommunity(Community community);

    List<IndexedCommunity> findAllByName(String name);
    List<IndexedCommunity> findAllByDescription(String description);
}
