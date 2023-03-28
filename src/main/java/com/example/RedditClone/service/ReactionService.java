package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.reaction.request.ReactionCreateRequestDTO;
import com.example.RedditClone.model.dto.reaction.response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.model.entity.Reaction;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReactionService {

    void deleteAllByPost(Post post);
    List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id);
    Reaction findById(Integer reactionId);
    Reaction createReaction(ReactionCreateRequestDTO reactionCreateRequestDTO, Authentication authentication);
    void delete(Reaction reaction);
    void save(Reaction reaction);
}
