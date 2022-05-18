package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Flair;
import com.example.RedditClone.Repository.FlairRepository;
import com.example.RedditClone.Service.FlairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlairServiceImpl implements FlairService {

    private final FlairRepository flairRepository;

    public FlairServiceImpl(FlairRepository flairRepository) {
        this.flairRepository = flairRepository;
    }

    @Override
    public Flair findFlairByName(String name) {
        return flairRepository.findFlairByName(name);
    }
}
