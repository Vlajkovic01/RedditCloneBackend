package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Flair;
import com.example.RedditClone.Repository.FlairRepository;
import com.example.RedditClone.Service.FlairService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
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
