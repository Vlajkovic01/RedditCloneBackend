package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Banned.Request.BannedCreateDTO;
import com.example.RedditClone.Model.Entity.Banned;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.BannedRepository;
import com.example.RedditClone.Repository.CommunityRepository;
import com.example.RedditClone.Repository.UserRepository;
import com.example.RedditClone.Service.BannedService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.ModeratorService;
import com.example.RedditClone.Service.UserService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BannedServiceImpl implements BannedService {
    private final ModeratorService moderatorService;
    private final UserService userService;
    private final CommunityRepository communityRepository;
    private final LogService logService;
    private final BannedRepository bannedRepository;

    public BannedServiceImpl(BannedRepository bannedRepository, LogService logService, CommunityRepository communityRepository, UserService userService, ModeratorService moderatorService) {
        this.bannedRepository = bannedRepository;
        this.logService = logService;
        this.communityRepository = communityRepository;
        this.userService = userService;
        this.moderatorService = moderatorService;
    }

    @Override
    public Banned createBan(BannedCreateDTO bannedCreateDTO, Authentication authentication) {
        logService.message("Banned service, createBan() method called.", MessageType.INFO);

        Community community = communityRepository.findCommunityById(bannedCreateDTO.getCommunityId());
        User userForBan = userService.findUserById(bannedCreateDTO.getUserId());
        Banned banned = bannedRepository.findBannedByCommunityIdAndUserId(bannedCreateDTO.getCommunityId(), bannedCreateDTO.getUserId());

        if (banned != null) {
            return null;
        }
        if(community == null){
            return null;
        }
        if(userForBan == null){
            return null;
        }
        if (authentication == null) {
            logService.message("Banned service, createBan() method, authentication is null.", MessageType.WARN);
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Banned service, createBan() method, current logged user is null.", MessageType.WARN);
            return null;
        }
        if (!moderatorService.amIModerator(community.getId(), currentLoggedUser.getId())) {
            logService.message("Banned service, createBan() method, current logged user not a moderator.", MessageType.WARN);
            return null;
        }

        Banned newBan = new Banned();
        newBan.setTimestamp(LocalDate.now());
        newBan.setBy(currentLoggedUser);
        newBan.setUser(userForBan);
        newBan.setCommunity(community);

        bannedRepository.save(newBan);
        return newBan;
    }

    @Override
    public List<Banned> findAllByCommunityId(Integer communityId) {
        return bannedRepository.findAllByCommunityId(communityId);
    }

    @Override
    public Banned findBannedByCommunityIdAndUserUsername(Integer communityId, String username) {
        return bannedRepository.findBannedByCommunityIdAndUserUsername(communityId, username);
    }

    @Override
    public boolean delete(Integer communityId, String username) {
        Banned banForDelete = bannedRepository.findBannedByCommunityIdAndUserUsername(communityId, username);
        System.out.println(banForDelete);

        if (banForDelete == null) {
            return false;
        }

        bannedRepository.delete(banForDelete);
        return true;
    }
}
