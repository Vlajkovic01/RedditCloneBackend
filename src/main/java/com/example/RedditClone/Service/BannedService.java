package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Banned.Request.BannedCreateDTO;
import com.example.RedditClone.Model.Entity.Banned;
import org.springframework.security.core.Authentication;

public interface BannedService {
    Banned createBan(BannedCreateDTO bannedCreateDTO, Authentication authentication);
}
