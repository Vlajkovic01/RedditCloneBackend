package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.indexedCommunity.response.IndexedCommunityResponseDTO;
import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.indexed.IndexedCommunity;

import java.util.List;
import java.util.Map;

public interface IndexedCommunityService {

    void indexCommunity(Community community, String pdfText);
    void updateNumOfPostAndAvgKarma(Community community, Integer numPosts);
    void updateAvgKarma(Community community);
    List<IndexedCommunityResponseDTO> search (Map<String ,String> params) throws IllegalArgumentException;

    void deleteById(Integer id);
    IndexedCommunity findById(Integer id);
}
