package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public void deleteAllByPost(Post post) {
        reactionRepository.deleteAllByPost(post);
    }
}
