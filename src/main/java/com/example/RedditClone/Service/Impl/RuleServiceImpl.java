package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Rule.Response.RuleGetByCommunityResponseDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Rule;
import com.example.RedditClone.Repository.RuleRepository;
import com.example.RedditClone.Service.RuleService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private final ExtendedModelMapper modelMapper;
    private final RuleRepository ruleRepository;

    public RuleServiceImpl(RuleRepository ruleRepository, ExtendedModelMapper modelMapper) {
        this.ruleRepository = ruleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RuleGetByCommunityResponseDTO> findAllByCommunity(Community community) {
        List<Rule> rules = ruleRepository.findAllByCommunity(community);
        return modelMapper.mapAll(rules, RuleGetByCommunityResponseDTO.class);
    }
}
