package com.example.RedditClone.service;

import com.example.RedditClone.model.entity.Moderator;

public interface ModeratorService {

    boolean amIModerator(Integer idCommunity, Integer idUser);
    void delete(Moderator moderator);
    void save(Moderator moderator);

    boolean deleteModeratorFromCommunity(Integer communityId, Integer moderatorId);
}
