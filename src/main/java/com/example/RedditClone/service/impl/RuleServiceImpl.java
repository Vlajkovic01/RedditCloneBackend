package com.example.RedditClone.service.impl;

import com.example.RedditClone.model.dto.rule.response.RuleGetByCommunityResponseDTO;
import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Rule;
import com.example.RedditClone.repository.jpa.RuleRepository;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.service.RuleService;
import com.example.RedditClone.model.mapper.ExtendedModelMapper;
import com.example.RedditClone.model.enumeration.MessageType;
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
