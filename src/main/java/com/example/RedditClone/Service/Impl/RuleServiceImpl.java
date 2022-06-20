package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Rule.Response.RuleGetByCommunityResponseDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Rule;
import com.example.RedditClone.Repository.RuleRepository;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.RuleService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
    private final LogService logService;
    private final ExtendedModelMapper modelMapper;
    private final RuleRepository ruleRepository;

    public RuleServiceImpl(RuleRepository ruleRepository, ExtendedModelMapper modelMapper, LogService logService) {
        this.ruleRepository = ruleRepository;
        this.modelMapper = modelMapper;
        this.logService = logService;
    }

    @Override
    public List<RuleGetByCommunityResponseDTO> findAllByCommunity(Community community) {

        logService.message("Rule service, findAllByCommunity() method called.", MessageType.INFO);

        List<Rule> rules = ruleRepository.findAllByCommunity(community);
        return modelMapper.mapAll(rules, RuleGetByCommunityResponseDTO.class);
    }
}
