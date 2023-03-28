package com.example.RedditClone.service.impl;

import com.example.RedditClone.model.entity.Flair;
import com.example.RedditClone.repository.jpa.FlairRepository;
import com.example.RedditClone.service.FlairService;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.stereotype.Service;

@Service
public class FlairServiceImpl implements FlairService {
    private final LogService logService;
    private final FlairRepository flairRepository;

    public FlairServiceImpl(FlairRepository flairRepository, LogService logService) {
        this.flairRepository = flairRepository;
        this.logService = logService;
    }

    @Override
    public Flair findFlairByName(String name) {
        logService.message("Flair service, findFlairByName() method called.", MessageType.INFO);
        return flairRepository.findFlairByName(name);
    }
}
