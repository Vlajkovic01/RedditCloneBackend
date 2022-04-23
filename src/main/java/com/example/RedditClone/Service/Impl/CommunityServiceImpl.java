package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Repository.CommunityRepository;
import com.example.RedditClone.Service.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public List<Community> findAll() {
        return communityRepository.findAll();
    }
}
