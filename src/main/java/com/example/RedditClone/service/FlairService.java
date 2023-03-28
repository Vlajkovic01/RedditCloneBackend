package com.example.RedditClone.service;

import com.example.RedditClone.model.entity.Flair;

public interface FlairService {

    Flair findFlairByName(String name);
}
