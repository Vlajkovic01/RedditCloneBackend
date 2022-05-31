package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunityEditRequestDTO;
import com.example.RedditClone.Model.Entity.*;
import com.example.RedditClone.Repository.CommunityRepository;
import com.example.RedditClone.Repository.FlairRepository;
import com.example.RedditClone.Repository.RuleRepository;
import com.example.RedditClone.Service.CommunityService;
import com.example.RedditClone.Service.FlairService;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final RuleRepository ruleRepository;

    private final FlairRepository flairRepository;
    private final UserService userService;

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserService userService, FlairRepository flairRepository, RuleRepository ruleRepository) {
        this.communityRepository = communityRepository;
        this.userService = userService;
        this.flairRepository = flairRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Override
    public List<Community> find12RandomCommunities() {
        return communityRepository.find12RandomCommunities();
    }

    @Override
    public Community findCommunityById(Integer id) {
        return communityRepository.findCommunityById(id);
    }

    @Override
    public Community createCommunity(CommunityCreateRequestDTO communityCreateRequestDTO, Authentication authentication) {
        Community community = communityRepository.findCommunityByName(communityCreateRequestDTO.getName());

        if(community != null){
            return null;
        }
        if (authentication == null) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return null;
        }
        Community newCommunity = new Community();
        newCommunity.setName(communityCreateRequestDTO.getName());
        newCommunity.setDescription(communityCreateRequestDTO.getDescription());
        newCommunity.setCreationDate(LocalDate.now());
        newCommunity.setIsSuspended(false);
        newCommunity.setSuspendedReason(null);

        Moderator moderator = new Moderator();
        moderator.setUser(currentLoggedUser);
        moderator.setCommunity(newCommunity);

        newCommunity.getModerators().add(moderator);

        Community finalNewCommunity = newCommunity;
        Set<Flair> flairs = communityCreateRequestDTO.getFlairs().stream().map(flair -> {
           Flair f = new Flair();
           f.getCommunities().add(finalNewCommunity);
           f.setName(flair.getName());
           return this.flairRepository.save(f);
        }).collect(Collectors.toSet());

        newCommunity.setFlairs(flairs);
        finalNewCommunity.setFlairs(flairs);

        newCommunity = communityRepository.save(newCommunity);

        Set<Rule> rules = communityCreateRequestDTO.getRules().stream().map(rule -> {
            Rule r = new Rule();
            r.setCommunity(finalNewCommunity);
            r.setDescription(rule.getDescription());
            return this.ruleRepository.save(r);
        }).collect(Collectors.toSet());

        newCommunity.setRules(rules);
        finalNewCommunity.setRules(rules);

        return newCommunity;
    }

    @Override
    public Community editCommunity(CommunityEditRequestDTO communityEditRequestDTO, Community community) {
        community.setDescription(communityEditRequestDTO.getDescription());
        Community finalCommunity = community;
        Set<Flair> flairs = communityEditRequestDTO.getFlairs().stream().map(flair -> {
            Flair f = new Flair();
            f.getCommunities().add(finalCommunity);
            f.setName(flair.getName());
            return this.flairRepository.save(f);
        }).collect(Collectors.toSet());
        community.setFlairs(flairs);

        ruleRepository.deleteAllByCommunityId(finalCommunity.getId());
        Set<Rule> rules = communityEditRequestDTO.getRules().stream().map(rule -> {
            Rule r = new Rule();
            r.setCommunity(finalCommunity);
            r.setDescription(rule.getDescription());
            return this.ruleRepository.save(r);
        }).collect(Collectors.toSet());

        community = communityRepository.save(community);
        return community;
    }

    @Override
    public Community save(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public void deleteCommunity(Community community) {
        communityRepository.delete(community);
    }

}
