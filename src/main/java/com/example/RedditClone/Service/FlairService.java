package com.example.RedditClone.Service;

import com.example.RedditClone.Model.Entity.Flair;

public interface FlairService {

    Flair findFlairByName(String name);
}
