package com.example.RedditClone.service.impl;

import com.example.RedditClone.model.dto.community.request.CommunityCreateRequestDTO;
import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.indexed.IndexedCommunity;
import com.example.RedditClone.model.mapper.IndexedCommunityMapper;
import com.example.RedditClone.repository.elasticsearch.IndexedCommunityRepository;
import com.example.RedditClone.service.IndexedCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexedCommunityServiceImpl implements IndexedCommunityService {

    private final IndexedCommunityRepository indexedCommunityRepository;

    public IndexedCommunityServiceImpl(IndexedCommunityRepository indexedCommunityRepository) {
        this.indexedCommunityRepository = indexedCommunityRepository;
    }

    @Override
    public void indexCommunity(Community community) {
        indexedCommunityRepository.save(IndexedCommunityMapper.mapIndexedCommunity(community));
    }

    @Override
    public List<IndexedCommunity> findAllByName(String name) {
        return indexedCommunityRepository.findAllByName(name);
    }

    @Override
    public List<IndexedCommunity> findAllByDescription(String description) {
        return indexedCommunityRepository.findAllByDescription(description);
    }
}
