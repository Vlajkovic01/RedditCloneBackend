package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Flair;
import com.example.RedditClone.Model.Entity.Moderator;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.CommunityRepository;
import com.example.RedditClone.Repository.FlairRepository;
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

    private final FlairRepository flairRepository;
    private final UserService userService;

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserService userService, FlairRepository flairRepository) {
        this.communityRepository = communityRepository;
        this.userService = userService;
        this.flairRepository = flairRepository;
    }

    @Override
    public List<Community> findAll() {
        return communityRepository.findAll();
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

//        newCommunity = communityRepository.save(newCommunity);

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
        return newCommunity;
    }

}
