package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReactionService {

    void deleteAllByPost(Post post);
    List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id);
    Reaction createReaction(ReactionCreateRequestDTO reactionCreateRequestDTO, Authentication authentication);
}
