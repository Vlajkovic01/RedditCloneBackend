package com.example.RedditClone.service.impl;

import com.example.RedditClone.lucene.handlers.PDFHandler;
import com.example.RedditClone.model.dto.community.request.CommunityCreateRequestDTO;
import com.example.RedditClone.model.dto.community.request.CommunityEditRequestDTO;
import com.example.RedditClone.model.dto.community.request.CommunitySuspendRequestDTO;
import com.example.RedditClone.model.entity.*;
import com.example.RedditClone.model.indexed.IndexedCommunity;
import com.example.RedditClone.repository.jpa.CommunityRepository;
import com.example.RedditClone.repository.jpa.FlairRepository;
import com.example.RedditClone.repository.jpa.ModeratorRepository;
import com.example.RedditClone.repository.jpa.RuleRepository;
import com.example.RedditClone.service.*;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final LogService logService;
    private final RuleRepository ruleRepository;
    private final FlairRepository flairRepository;
    private final UserService userService;
    private final CommunityRepository communityRepository;
    private final IndexedCommunityService indexedCommunityService;
    private final IndexedPostService indexedPostService;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserService userService, FlairRepository flairRepository, RuleRepository ruleRepository, LogService logService, IndexedCommunityService indexedCommunityService, IndexedPostService indexedPostService) {
        this.communityRepository = communityRepository;
        this.userService = userService;
        this.flairRepository = flairRepository;
        this.ruleRepository = ruleRepository;
        this.logService = logService;
        this.indexedCommunityService = indexedCommunityService;
        this.indexedPostService = indexedPostService;
    }

    @Override
    public List<Community> findAll() {
        logService.message("Community service, findAll() method called.", MessageType.INFO);
        return communityRepository.findAll();
    }

    @Override
    public List<Community> find12RandomCommunities() {
        logService.message("Community service, find12RandomCommunities() method called.", MessageType.INFO);
        return communityRepository.find12RandomCommunities();
    }

    @Override
    public Community findCommunityById(Integer id) {
        logService.message("Community service, findCommunityById() method called.", MessageType.INFO);
        return communityRepository.findCommunityById(id);
    }

    @Override
    public Community createCommunity(CommunityCreateRequestDTO communityCreateRequestDTO, Authentication authentication) {

        logService.message("Community service, createCommunity() method called.", MessageType.INFO);

        Community community = communityRepository.findCommunityByName(communityCreateRequestDTO.getName());

        if(community != null){
            return null;
        }
        if (authentication == null) {
            logService.message("Community service, createCommunity() method, authentication is null.", MessageType.WARN);
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Community service, createCommunity() method, current logged user is null.", MessageType.WARN);
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
            Flair foundFlair = flairRepository.findFlairByName(flair.getName());
            if (foundFlair != null) {
                foundFlair.getCommunities().add(finalNewCommunity);
                return this.flairRepository.save(foundFlair);
            } else {
                Flair f = new Flair();
                f.getCommunities().add(finalNewCommunity);
                f.setName(flair.getName());
                return this.flairRepository.save(f);
            }
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

        if (communityCreateRequestDTO.getPdf() == null) {
            indexedCommunityService.indexCommunity(newCommunity, "");
        } else {
            newCommunity.setPdfFileName(communityCreateRequestDTO.getPdf().getFilename());
            newCommunity = communityRepository.save(newCommunity);
            indexedCommunityService.indexCommunity(newCommunity, communityCreateRequestDTO.getPdf().getPdfText());
        }

        return newCommunity;
    }

    @Override
    public Community editCommunity(CommunityEditRequestDTO communityEditRequestDTO, Community community) {

        logService.message("Community service, editCommunity() method called.", MessageType.INFO);

        community.setDescription(communityEditRequestDTO.getDescription());
        Community finalCommunity = community;
        Set<Flair> flairs = communityEditRequestDTO.getFlairs().stream().map(flair -> {
            Flair foundFlair = flairRepository.findFlairByName(flair.getName());
            if (foundFlair != null) {
                foundFlair.getCommunities().add(finalCommunity);
                return this.flairRepository.save(foundFlair);
            } else {
                Flair f = new Flair();
                f.getCommunities().add(finalCommunity);
                f.setName(flair.getName());
                return this.flairRepository.save(f);
            }
        }).collect(Collectors.toSet());
        community.setFlairs(flairs);

        ruleRepository.deleteAllByCommunityId(community.getId());
        Set<Rule> rules = communityEditRequestDTO.getRules().stream().map(rule -> {
            Rule r = new Rule();
            r.setCommunity(finalCommunity);
            r.setDescription(rule.getDescription());
            return this.ruleRepository.save(r);
        }).collect(Collectors.toSet());
        community.setRules(rules);

        if (communityEditRequestDTO.getPdf() == null) {
            community = communityRepository.save(community);
            IndexedCommunity indexedCommunity = indexedCommunityService.findById(community.getId());
            indexedCommunityService.indexCommunity(community, indexedCommunity.getPdfText());
        } else {
            community.setPdfFileName(communityEditRequestDTO.getPdf().getFilename());
            community = communityRepository.save(community);
            indexedCommunityService.indexCommunity(community, communityEditRequestDTO.getPdf().getPdfText());
        }
        return community;
    }

    @Override
    public Community save(Community community) {
        logService.message("Community service, save() method called.", MessageType.INFO);
        return communityRepository.save(community);
    }

    @Override
    public void suspendCommunity(Community community, CommunitySuspendRequestDTO communitySuspendRequestDTO) {

        logService.message("Community service, suspendCommunity() method called.", MessageType.INFO);

        community.getModerators().forEach(moderator -> {
            moderator.setCommunity(null);
        });
        community.getModerators().clear();

        community.setIsSuspended(true);
        community.setSuspendedReason(communitySuspendRequestDTO.getSuspendedReason());

        communityRepository.save(community);
        indexedCommunityService.deleteById(community.getId());
        indexedPostService.deleteIndexedPostsByCommunityId(community.getId());
    }

}
