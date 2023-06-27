package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.indexedCommunity.response.IndexedCommunityResponseDTO;
import com.example.RedditClone.model.entity.Community;

import java.util.List;
import java.util.Map;

public interface IndexedCommunityService {

    void indexCommunity(Community community, String pdfText);
    void updateNumOfPostAndAvgKarma(Community community);
    void updateAvgKarma(Community community);
    List<IndexedCommunityResponseDTO> search (Map<String ,String> params) throws IllegalArgumentException;
}
