package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Repository.ModeratorRepository;
import com.example.RedditClone.Service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    private final ModeratorRepository moderatorRepository;

    public ModeratorServiceImpl(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    @Override
    public boolean amIModerator(Integer idCommunity, Integer idUser) {
        return moderatorRepository.imIModerator(idCommunity, idUser) > 0;
    }
}
