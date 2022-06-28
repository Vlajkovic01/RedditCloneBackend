package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Moderator;
import com.example.RedditClone.Repository.CommunityRepository;
import com.example.RedditClone.Repository.ModeratorRepository;
import com.example.RedditClone.Service.CommunityService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.ModeratorService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final CommunityRepository communityRepository;
    private final LogService logService;
    private final ModeratorRepository moderatorRepository;

    public ModeratorServiceImpl(ModeratorRepository moderatorRepository, LogService logService, CommunityRepository communityRepository) {
        this.moderatorRepository = moderatorRepository;
        this.logService = logService;
        this.communityRepository = communityRepository;
    }

    @Override
    public boolean amIModerator(Integer idCommunity, Integer idUser) {
        logService.message("Moderator service, amIModerator() method called.", MessageType.INFO);
        return moderatorRepository.imIModerator(idCommunity, idUser) > 0;
    }

    @Override
    public void delete(Moderator moderator) {
        logService.message("Moderator service, delete() method called.", MessageType.INFO);
        moderatorRepository.delete(moderator);
    }

    @Override
    public void save(Moderator moderator) {
        logService.message("Moderator service, save() method called.", MessageType.INFO);
        moderatorRepository.save(moderator);
    }

    @Override
    public boolean deleteModeratorFromCommunity(Integer communityId, Integer moderatorId) {
        logService.message("Moderator service, deleteModeratorFromCommunity() method called.", MessageType.INFO);

        Community community = communityRepository.findCommunityById(communityId);
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);

        if (community == null || moderator == null) {
            return false;
        }

        community.getModerators().removeIf(m -> m.getId().equals(moderator.getId()));

        communityRepository.save(community);

        return true;
    }
}
