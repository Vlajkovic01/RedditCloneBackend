package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Moderator;
import com.example.RedditClone.Repository.ModeratorRepository;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.ModeratorService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final LogService logService;
    private final ModeratorRepository moderatorRepository;

    public ModeratorServiceImpl(ModeratorRepository moderatorRepository, LogService logService) {
        this.moderatorRepository = moderatorRepository;
        this.logService = logService;
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
}
