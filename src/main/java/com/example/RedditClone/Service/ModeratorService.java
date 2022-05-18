package com.example.RedditClone.Service;

public interface ModeratorService {

    boolean amIModerator(Integer idCommunity, Integer idUser);
}
