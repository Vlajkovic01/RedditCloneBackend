package com.example.RedditClone.Service;

import com.example.RedditClone.Model.Entity.Moderator;

public interface ModeratorService {

    boolean amIModerator(Integer idCommunity, Integer idUser);
    void delete(Moderator moderator);
    void save(Moderator moderator);
}
