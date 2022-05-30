package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.ReactionService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ExtendedModelMapper modelMapper;

    private final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, ExtendedModelMapper modelMapper) {
        this.reactionRepository = reactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteAllByPost(Post post) {
        reactionRepository.deleteAllByPost(post);
    }

    @Override
    public List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id) {
        List<Reaction> reactions = reactionRepository.findAllByPostId(id);
        return modelMapper.mapAll(reactions, ReactionForPostAndCommentDTO.class);
    }
}
